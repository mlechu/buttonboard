package model;

import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.*;
import javax.speech.synthesis.*;

// Represents a playable text-to-speech sound
public class TextToSpeechSound extends Sound {
    private String phrase;

    public TextToSpeechSound(String phrase) {
        this.phrase = phrase;
    }

    // GETTERS

    public String getPhrase() {
        return this.phrase;
    }

    // METHODS

    // REQUIRES: Well-formed English word or phrase according to FreeTTS
    // EFFECTS: Plays the TextToSpeechSound
    @Override
    public void play() {
        new TextToSpeechSynth(phrase);
    }

}

