package persistence;

import exceptions.EmptyInputException;
import exceptions.IllegalCharacterException;
import model.Button;
import model.ButtonBoard;
import model.TextToSpeechSound;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents a reader that can read SoundAppPanel data from a file
// Based on TellerApp
public class Reader {
    public static final String DELIMITER = ","; //TODO: Make sure commas in user input don't break anything

    // EFFECTS: returns a list of boards parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static List<ButtonBoard> readBoards(File file) throws IOException {
        List<String> lines = separateFileByLine(file);
        List<ButtonBoard> result;
        try {
            result = linesToBoards(lines);

        } catch (IllegalCharacterException | EmptyInputException e) {
            System.out.println("Error in parsing save file");
            e.printStackTrace();
            result = new ArrayList<ButtonBoard>();
        }
        return result;
    }

    // EFFECTS: returns list of strings representing text file separated into lines
    private static List<String> separateFileByLine(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of boards parsed from list of string where each string contains data for one board
    //          if illegal characters are included, IllegalCharacterException is thrown
    //          if any input is empty, EmptyInputException is thrown
    private static List<ButtonBoard> linesToBoards(List<String> fileContent) //
            throws IllegalCharacterException, EmptyInputException {
        List<ButtonBoard> boards = new ArrayList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            boards.add(lineToBoard(lineComponents));
        }

        return boards;
    }

    // EFFECTS: returns a list of substrings obtained by splitting one line with DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] partsOfString = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(partsOfString));
    }

    // REQUIRES: In components, element 0 represents the name of the board.
    // After that, buttons are represented, each with 2 elements (name and sound).
    // EFFECTS: Takes a line of text and returns a board given legal input
    //          if illegal characters are included, IllegalCharacterException is thrown
    //          if any input is empty, EmptyInputException is thrown
    private static ButtonBoard lineToBoard(List<String> components)
            throws IllegalCharacterException, EmptyInputException {
        ButtonBoard loadingBoard = new ButtonBoard();
        loadingBoard.setName(components.get(0));

        int numberOfButtonsInLine = ((components.size() - 1) / 2); // - board name, /2 fields per button

        for (int i = 1; i <= numberOfButtonsInLine; i++) {
            Button loadingButton = new Button();
            try {
                loadingButton.setName(components.get((i * 2) - 1));
                loadingButton.setSound(components.get(i * 2)); //TODO: files
            } catch (IllegalCharacterException ice) {
                System.out.println("Illegal characters found within components");
                ice.printStackTrace();
            } catch (EmptyInputException eie) {
                System.out.println("Empty component found");
                eie.printStackTrace();
            }

            loadingBoard.addButton(loadingButton);
        }

        return loadingBoard;

    }
}
