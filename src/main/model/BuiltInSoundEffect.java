package model;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

// Represents a built-in sound effect from a file within the project structure
public class BuiltInSoundEffect extends Sound {
    private File soundFile;

    public BuiltInSoundEffect(String filePath) {
        soundFile = new File(filePath);
    }

    // EFFECTS: Plays sound effect at filePath
    @Override
    public void play() {
        try {
            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();
            Thread.sleep(1000);

        } catch (Exception e) {
            System.out.println("Couldn't play the sound effect");
        }
    }
}
