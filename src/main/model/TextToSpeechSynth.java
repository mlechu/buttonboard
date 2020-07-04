package model;

import javax.speech.Central;
import javax.speech.EngineCreate;
import javax.speech.EngineException;
import javax.speech.EngineList;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.Voice;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

// Represents all the tools needed to generate text to speech sounds using freeTTS
// This class bridges the gap between my own code and the imported libraries
public class TextToSpeechSynth {
    Synthesizer synthesizer;
    String voiceName = "kevin16";
    Voice voice;
    SynthesizerModeDesc desc;
    EngineList engineList;

    // EFFECTS: Creates, sets up, plays, and then discards a TextToSpeechSound
    public TextToSpeechSynth(String phrase) {
        try {
            initializeSynth();

            EngineCreate creator = (EngineCreate) engineList.get(0);

            synthesizer = (Synthesizer) creator.createEngine();
            synthesizer.allocate();
            synthesizer.resume();
            desc = (SynthesizerModeDesc) synthesizer.getEngineModeDesc();

            Voice[] voices = desc.getVoices();
            for (int i = 0; i < voices.length; i++) {
                if (voices[i].getName().equals(voiceName)) {
                    voice = voices[i];
                    break;
                }
            }
            synthesizer.getSynthesizerProperties().setVoice(voice);
            System.out.print("Speaking : " + phrase);
            synthesizer.speakPlainText(phrase, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
            synthesizer.deallocate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: Sets system properties so voices can be retrieved
    public void initializeSynth() throws EngineException {
        // Set property as Kevin Dictionary, register engine
        System.setProperty("FreeTTSSynthEngineCentral", "com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
        desc = new SynthesizerModeDesc(null, "general", Locale.US, null, null);
        synthesizer = Central.createSynthesizer(desc);
        engineList = Central.availableSynthesizers(desc);
    }
}


