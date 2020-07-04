package ui;

import exceptions.EmptyInputException;
import exceptions.IllegalCharacterException;
import model.ButtonBoard;
import model.SoundApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Represents the top menu bar in the application including:
// A dropdown "current board" menu with all the ButtonBoards, as well as add and delete buttons to manage them
// A dropdown "file" menu with options to save and load the application state
// Two buttons (for adding and deleting sounds from the current board)
// A "quit" button
public class MenuBar extends JMenuBar {

    private SoundApplication soundApp;

    private JMenu boardsMenu;
    private JMenuItem addBoardMenuItem;
    private JMenuItem removeBoardMenuItem;

    private JMenu fileMenu;
    private JMenuItem saveFile;
    private JMenuItem loadFile;

    private JButton addSoundButton = new JButton("+ Add sound...");
    private JButton deleteSoundButton = new JButton("- Delete sound...");
    private JButton quitButton = new JButton("Quit");

    public MenuBar(SoundApplication app) {
        super();
        this.soundApp = app;
        initializeMenuBarDropdown(soundApp.getBoards());
        initializeMenuBarFileDropdown();
        initializeMenuBarButtons();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Adds boards dropdown menu to menu bar
    protected void initializeMenuBarDropdown(ArrayList<ButtonBoard> boards) {
        boardsMenu = new JMenu("Current board: " + soundApp.getCurrentBoard().getName());
        this.add(boardsMenu);
        boardsMenu.add(addBoardMenuItem = new JMenuItem("+ Add a board"));
        boardsMenu.add(removeBoardMenuItem = new JMenuItem("- Remove a board"));
        //boardsMenu.add(new JToolBar.Separator()); //TODO: this currently breaks the setting of listeners
        for (ButtonBoard b : boards) {
            boardsMenu.add(new JMenuItem(b.getName()));
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds file dropdown menu to menu bar
    private void initializeMenuBarFileDropdown() {
        fileMenu = new JMenu("File");
        this.add(fileMenu);
        fileMenu.add(saveFile = new JMenuItem("Save state"));
        fileMenu.add(loadFile = new JMenuItem("Load state"));
    }

    // MODIFIES: this
    // EFFECTS: Adds remaining buttons to the menu bar (add sound, delete sound, and quit)
    private void initializeMenuBarButtons() {
        this.add(addSoundButton);
        this.add(deleteSoundButton);
        this.add(new JLabel("                              ")); //TODO: find a better spacer/alignment method lol
        this.add(quitButton);
    }

    // MODIFIES: this
    // EFFECTS: Sets ActionListeners for the Boards menu
    public void setDropdownActionListeners() {
        for (int i = 0; i < boardsMenu.getItemCount(); i++) {
            JMenuItem dropdownMenuItem = boardsMenu.getItem(i);
            if (dropdownMenuItem == addBoardMenuItem) {
                dropdownMenuItem.addActionListener(new AddBoardActionHandler("[no name]"));
            } else if (dropdownMenuItem == removeBoardMenuItem) {
                dropdownMenuItem.addActionListener(new RemoveBoardActionHandler(i));
            } else {
                dropdownMenuItem.addActionListener(new ChooseBoardActionHandler(i - 2));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets ActionListeners for the File dropdown menu
    public void setMenuBarFileDropdownActionListeners() {
        saveFile.addActionListener(new SaveFileActionHandler());
        loadFile.addActionListener(new LoadFileActionHandler());
    }

    // MODIFIES: this
    // EFFECTS: Sets ActionListeners for the remaining buttons on the menu bar
    //          (Add sound, delete sound, quit)
    public void setMenuBarButtonActionListeners() {
        addSoundButton.addActionListener(new AddSoundActionHandler("[no name]", "the sound of silence"));
        deleteSoundButton.addActionListener(new DeleteSoundActionHandler());
        quitButton.addActionListener(new QuitActionHandler());
    }

    // Represents an action handler for the "add board" button in the boards dropdown menu
    protected class AddBoardActionHandler implements ActionListener {
        private String name;

        AddBoardActionHandler(String defaultName) {
            this.name = defaultName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String inputName = JOptionPane.showInputDialog(soundApp.soundAppPanel,
                    "What will the board's name be?");
            try {
                soundApp.addBoard(inputName);
            } catch (IllegalCharacterException | EmptyInputException ex) {
                System.out.println("Bad input. Defaults used.");
                try {
                    soundApp.addBoard(name);
                } catch (IllegalCharacterException | EmptyInputException eee) {
                    System.out.println("Defaults not working. Abort mission");
                }
            }

        }
    }

    // Represents an action handler for the "remove board" button in the boards dropdown menu
    protected class RemoveBoardActionHandler implements ActionListener {
        protected int boardNumber;

        RemoveBoardActionHandler(int index) {
            this.boardNumber = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            soundApp.removeBoard(0);
        } //TODO
    }

    // Represents an action handler for any of the boards in the boards dropdown menu
    protected class ChooseBoardActionHandler implements ActionListener {
        protected int boardNumber;

        ChooseBoardActionHandler(int index) {
            this.boardNumber = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //soundAppPanel.setCurrentBoard(soundAppPanel.getBoards().get(boardNumber));
            soundApp.setCurrentBoard(boardNumber);
            //soundAppPanel.resetAllGraphics();
        }
    }

    // Represents an action handler for the Add Sound button
    protected class AddSoundActionHandler implements ActionListener {
        protected String name;
        protected String phrase;

        AddSoundActionHandler(String defaultName, String defaultPhrase) {
            this.name = defaultName;
            this.phrase = defaultPhrase;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String inputName = JOptionPane.showInputDialog(soundApp.soundAppPanel,
                    "What will the button's name be?");
            String inputPhrase = JOptionPane.showInputDialog(soundApp.soundAppPanel,
                    "What will the button say when pressed?");
            try {
                soundApp.addSound(inputName, inputPhrase);
            } catch (IllegalCharacterException | EmptyInputException eeee) {
                System.out.println("Defaults used.");
                try {
                    soundApp.addSound(name, phrase);
                } catch (IllegalCharacterException | EmptyInputException ex) {
                    System.out.println("Defaults didn't work.");
                }
            }
        }
    }

    // Represents an action handler for the Delete Sound button
    protected class DeleteSoundActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            soundApp.deleteSound(0);
        } //TODO
    }

    // Represents an action handler for the Save State button
    protected class SaveFileActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            soundApp.saveBoardsToFile();
        }
    }

    // Represents an action handler for the Load State button
    protected class LoadFileActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            soundApp.loadBoardsFromFile();
        }
    }

    // Represents an action handler for the Quit button
    protected class QuitActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            soundApp.soundAppPanel.quit();
        }
    }
}