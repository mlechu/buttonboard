package model;

import exceptions.EmptyInputException;
import exceptions.IllegalCharacterException;
import persistence.Reader;

// Represents one button on a soundboard with a name, sound, and a color
public class Button {

    private String name;
//    private Sound sound;
    private TextToSpeechSound sound;

    public Button() { //TODO: name parameter
//        this.color = new Color(73, 45, 150); //TODO: User chooses color
//        this.jButton = new JButton();
    }

    // GETTERS
    public String getName() {
        return this.name;
    }

    // EFFECTS: Returns the button's Text-To-Speech sound
    public TextToSpeechSound getSound() {
        return this.sound;
    }

    // SETTERS

    // EFFECTS: Sets the name of the button given legal input
    //          if illegal characters are included, IllegalCharacterException is thrown
    //          if any input is empty, EmptyInputException is thrown
    public void setName(String name) throws IllegalCharacterException, EmptyInputException {
        if (name.equals("")) {
            throw new EmptyInputException();
        }
        if (name.contains(Reader.DELIMITER)) {
            throw new IllegalCharacterException();
        }
        this.name = name;
    }

    // EFFECTS: Sets the sound as a new TextToSpeech sound TODO: Refactor for files, other sounds
    //          if illegal characters are included, IllegalCharacterException is thrown
    //          if any input is empty, EmptyInputException is thrown
    public void setSound(String sound) throws IllegalCharacterException, EmptyInputException {
        if (sound.equals("")) {
            throw new EmptyInputException();
        }
        if (sound.contains(Reader.DELIMITER)) {
            throw new IllegalCharacterException();
        }
        this.sound = new TextToSpeechSound(sound);
    }

    // METHODS

    // EFFECTS: Plays sound and prints to console what has played
    public void playSound() {
        sound.play();
        System.out.println("\n" + this.name + " has played!\n");
    }

}