package model;

import java.util.ArrayList;
import java.io.PrintWriter;

import exceptions.EmptyInputException;
import exceptions.IllegalCharacterException;
import persistence.Saveable;
import persistence.Reader;

// Represents a set of Buttons, similar to a folder of Buttons with a name
public class ButtonBoard implements Saveable {

    private String name;
    public ArrayList<Button> buttons;

    public ButtonBoard() {
        this.buttons = new ArrayList<>();
    }

    // GETTERS

    public String getName() {
        return name;
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    } //TODO: test

    // SETTERS

    public void setName(String name) throws EmptyInputException, IllegalCharacterException {
        if (name.equals("")) {
            throw new EmptyInputException();
        }
        if (name.contains(Reader.DELIMITER)) {
            throw new IllegalCharacterException();
        }
        this.name = name;
    }

    // MODIFIES: this.buttons
    // EFFECTS: Adds a Button to this board
    public void addButton(Button button) {
        this.buttons.add(button);
    }

    // MODIFIES: this.buttons
    // EFFECTS: Removes a Button from this board
    public void removeButtonByIndex(int index) {
        if ((0 <= index) && (index < buttons.size())) {
            buttons.remove(buttons.get(index));
        } else {
            System.out.println("Invalid input");
        }

    }

    // EFFECTS: Returns the Button with the given index, first button index too small, last if too large
    public Button selectButtonByIndex(int index) {
        if (index < 0) {
            System.out.println("Index too small! First button selected.");
            return buttons.get(0);
        } else if (index >= buttons.size()) {
            System.out.println("Index too large! Last button selected.");
            return buttons.get(buttons.size() - 1);
        } else {
            return buttons.get(index);
        }
    }

    //EFFECTS: Saves ButtonBoard to a file in the form of a line
    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(this.name);
        printWriter.print(Reader.DELIMITER);
        for (Button b : buttons) {
            printWriter.print(b.getName());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(b.getSound().getPhrase());
            printWriter.print(Reader.DELIMITER);
        }
    }


}
