package ui;

import model.SoundApplication;

import javax.swing.*;

// Represents the JFrame used to display a soundboard application
public class SoundAppPanel extends JFrame {

    private final SoundApplication soundApplication;

    private static final String FRAME_TITLE = "ButtonBoard";
    private MenuBar topMenuBar;
    private BoardPanel boardPanel;


    public SoundAppPanel(SoundApplication soundApplication) {
        super(FRAME_TITLE);
        this.soundApplication = soundApplication;
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //repaint();
    }

    // MODIFIES: this
    // EFFECTS: Resets graphics to show the current board
    public void resetAllGraphics() {

        removeOldBoardPanel();
        prepareNewBoardPanel();

        prepareMenuBar();

        setJMenuBar(topMenuBar);
        add(boardPanel);
        pack();
        this.setVisible(true);
    }

    // MODIFIES: this, this.boardView
    // EFFECTS: If this panel contains an instance of BoardPanel, get rid of it
    //          Use when an updated BoardPanel is needed
    private void removeOldBoardPanel() {
        if (boardPanel != null) {
            this.remove(boardPanel);
            boardPanel = null;
        }
    }

    // MODIFIES: this.boardPanel, this
    // EFFECTS: Fully initializes and adds the boardPanel view
    private void prepareNewBoardPanel() {
        boardPanel = new BoardPanel(SoundApplication.getCurrentBoard());
        boardPanel.setSoundActionListeners();
    }

    // MODIFIES: this.topMenuBar, this
    // EFFECTS: Fully initializes and adds the menu bar
    private void prepareMenuBar() {
        topMenuBar = new MenuBar(soundApplication);
        topMenuBar.setDropdownActionListeners();
        topMenuBar.setMenuBarButtonActionListeners();
        topMenuBar.setMenuBarFileDropdownActionListeners();
    }

    // MODIFIES: this
    // EFFECTS: Stops the app after allowing the user to save data to file
    protected void quit() {
        int choice = JOptionPane.showOptionDialog(this, "Save the current state?",
                "quit menu", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, null, null);
        if (choice == 0) {
            soundApplication.saveBoardsToFile();
        }
        System.exit(0);
    }
}
