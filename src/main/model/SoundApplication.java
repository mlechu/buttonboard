package model;

import exceptions.EmptyInputException;
import exceptions.IllegalCharacterException;
import persistence.Reader;
import persistence.Saveable;
import persistence.Writer;
import ui.SoundAppPanel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SoundApplication implements Serializable {

    public final SoundAppPanel soundAppPanel;

    public static final BuiltInSoundEffect NEW_BOARD_SOUND = new BuiltInSoundEffect("./data/newBoardSound.wav");
    public static final String BOARDS_FILE = "./data/boards.txt";

    public static ArrayList<ButtonBoard> boards;
    //public static ButtonBoard currentBoardDeprecated = new ButtonBoard();
    public static int currentBoardIndex;

    public SoundApplication() {
        this.soundAppPanel = new SoundAppPanel(this);
        loadBoardsFromFile();
        soundAppPanel.resetAllGraphics();
    }

    public ArrayList<ButtonBoard> getBoards() {
        return boards;
    }

    public static ButtonBoard getCurrentBoard() {
        return selectBoard(currentBoardIndex); //currentBoardDeprecated;
    }

    public void setCurrentBoard(ButtonBoard newCurrentBoard) {
        currentBoardIndex = boards.indexOf(newCurrentBoard);
        //currentBoardDeprecated = newCurrentBoard;
    }

    public void setCurrentBoard(int index) {
        //currentBoardDeprecated = selectBoard(index);
        currentBoardIndex = index;
        soundAppPanel.resetAllGraphics();
    }

    // REQUIRES: *IF* there is a file in data named boards.txt, it must not be empty
    // MODIFIES: this
    // EFFECTS: Loads board data from boards.txt
    //          If there is nothing to load, initialize a new session
    public void loadBoardsFromFile() {
        try {
            List<ButtonBoard> retrievedBoards = Reader.readBoards(new File(BOARDS_FILE));
            boards = (ArrayList<ButtonBoard>) retrievedBoards;
            //currentBoardDeprecated = boards.get(0); //TODO: make this load from file as well
            currentBoardIndex = 0;
        } catch (IOException e) {
            try {
                initializeNewSession();
            } catch (IllegalCharacterException | EmptyInputException ex) {
                System.out.println("Failed to initialize new boards");
                ex.printStackTrace();
            }
        }
        soundAppPanel.resetAllGraphics();
    }

    // EFFECTS: Saves SoundAppPanel boards to file
    public void saveBoardsToFile() {
        try {
            Writer writer = new Writer(new File(BOARDS_FILE));
            for (Saveable b : boards) {
                writer.write(b);
            }
            writer.close();

            System.out.println("Boards saved to file " + BOARDS_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save boards to " + BOARDS_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }

    }

    // MODIFIES: this
    // EFFECTS: Starts up a new SoundAppPanel with a new board, NOT loading anything from a file
    public void initializeNewSession() throws EmptyInputException, IllegalCharacterException {
        ButtonBoard defaultBoard = new ButtonBoard();
        boards = new ArrayList<ButtonBoard>();

        defaultBoard.setName("My First Soundboard");
        boards.add(defaultBoard);
        //currentBoardDeprecated = defaultBoard;
        currentBoardIndex = boards.indexOf(defaultBoard);
        soundAppPanel.resetAllGraphics();
    }

    // MODIFIES: this.currentBoard
    // EFFECTS: Adds a new Button to the current ButtonBoard if valid input provided
    //          if illegal characters are included, IllegalCharacterException is thrown
    //          if any input is an empty string, EmptyInputException is thrown
    public void addSound(String buttonName, String phrase) throws IllegalCharacterException, EmptyInputException {
        Button myButton = new Button();

        myButton.setName(buttonName);
        myButton.setSound(phrase); //TODO: Sounds from files

        //currentBoardDeprecated.addButton(myButton);
        getCurrentBoard().addButton(myButton);
        System.out.println("Button created.\n");
        soundAppPanel.resetAllGraphics();
    }

    // MODIFIES: this.currentBoard
    // EFFECTS: Shows buttons and deletes chosen one if within range
    public void deleteSound(int index) {
        if ((0 <= index) && (index < getCurrentBoard().buttons.size())) {
            getCurrentBoard().removeButtonByIndex(index);
        } else {
            System.out.println("Invalid input");
        }
        soundAppPanel.resetAllGraphics();
    }

    // MODIFIES: this.boards
    // EFFECTS: Adds a new board to this.boards with given name
    //          if illegal characters are included, IllegalCharacterException is thrown
    //          if any input is an empty string, EmptyInputException is thrown
    public void addBoard(String name) throws IllegalCharacterException, EmptyInputException {
        NEW_BOARD_SOUND.play();
        ButtonBoard myBoard = new ButtonBoard();
        myBoard.setName(name);
        boards.add(myBoard);
        setCurrentBoard(myBoard);
        soundAppPanel.resetAllGraphics();
    }

    // MODIFIES: this.boards
    // EFFECTS: If there are 2 or more boards, shows boards and deletes chosen one if within range
    public void removeBoard(int index) {
        if (boards.size() > 1) {
            if ((0 <= index) && (index < boards.size())) {
                boards.remove(index);
                soundAppPanel.resetAllGraphics();
            } else {
                System.out.println("Invalid input");
            }
        } else {
            //new JFrame("You only have one board! Try adding more before deleting any.");
            System.out.println("You only have one board! Try adding more before deleting any.");
        }
    }

    // EFFECTS: Returns the ButtonBoard with the given index if valid;
    //          otherwise selects first board
    public static ButtonBoard selectBoard(int index) {
        if ((0 <= index) && (index < boards.size())) {
            return boards.get(index);
        } else {
            System.out.println("Navigating back to first board");
            return boards.get(0);
        }
    }

}