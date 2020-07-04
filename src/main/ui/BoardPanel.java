package ui;

import model.Button;
import model.ButtonBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Represents the view of a single ButtonBoard with all its buttons
public class BoardPanel extends JPanel {

    private ButtonBoard board;
    private ArrayList<JButton> buttonList;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    public BoardPanel(ButtonBoard board) {
        super();
        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //TODO: GridLayout instead
        this.setLayout(new GridLayout());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.LIGHT_GRAY);
        this.board = board;
        buttonList = new ArrayList<JButton>();
        renderButtons();
        setVisible(true);
    }

    // MODIFIES: this.jbuttons, this
    // EFFECTS: Places all buttons on this panel
    private void renderButtons() {
        for (Component c : this.getComponents()) {
            this.remove(c);
        }
        for (Button b : board.getButtons()) {
            JButton b1 = new JButton(b.getName());
            buttonList.add(b1);
            this.add(b1);
            b1.setVerticalTextPosition(AbstractButton.CENTER);
            b1.setHorizontalTextPosition(AbstractButton.CENTER);
        }
    }

    // MODIFIES: this.jbuttons
    // EFFECTS: Adds an ActionListener to each JButton in the list
    public void setSoundActionListeners() {
        for (int i = 0; i < buttonList.size(); i++) {
            JButton thisButton = buttonList.get(i);
            thisButton.addActionListener(new ButtonSoundActionHandler(i));
        }
    }

    // Represents an action handler for playing sounds by clicking buttons
    protected class ButtonSoundActionHandler implements ActionListener {
        protected int buttonNumber;

        ButtonSoundActionHandler(int index) {
            buttonNumber = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            board.selectButtonByIndex(buttonNumber).playSound(); //TODO
        }
    }

}