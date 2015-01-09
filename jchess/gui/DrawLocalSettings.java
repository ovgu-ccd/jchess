/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 */
package jchess.gui;

import jchess.StringResources;

import jchess.mvc.events.NewGame;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

/**
 * Class responsible for drawing the fold with local game settings
 */
class DrawLocalSettings extends JPanel implements ActionListener, TextListener {

    private JDialog parent;//needet to close newGame window
    private JComboBox color;//to choose color of player
    private JRadioButton oponentComp;//choose oponent
    private JRadioButton oponentHuman;//choose oponent (human)
    private ButtonGroup oponentChoos;//group 4 radio buttons
    JFrame localPanel;
    private JLabel compLevLab;
    private JSlider computerLevel;//slider to choose jChess Engine level
    private JTextField firstName;//editable field 4 nickname
    private JTextField secondName;//editable field 4 nickname
    private JLabel firstNameLab;
    private JLabel secondNameLab;
    private JCheckBox upsideDown;//if true draw chessboard upsideDown(white on top)
    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    Container cont;
    private JSeparator sep;
    private JButton okButton;
    private JCheckBox timeGame;
    private JComboBox time4Game;
    private String[] colors = {StringResources.MAIN.getString("white"), StringResources.MAIN.getString("black")};
    private String[] times = {"1", "3", "5", "8", "10", "15", "20", "25", "30", "60", "120"};

    private JTextField         thirdName;
    private JLabel             thirdNameLab;


    private NewGameWindow parPtr;


    DrawLocalSettings(NewGameWindow parent) {
        super();
        parPtr = parent;
        this.parent = parent;
        this.gbl = new GridBagLayout();
        this.gbc = new GridBagConstraints();
        this.sep = new JSeparator();
        this.okButton = new JButton(StringResources.MAIN.getString("ok"));

        this.firstName = new JTextField("", 10);
        this.firstName.setSize(new Dimension(200, 50));
        this.secondName = new JTextField("", 10);
        this.secondName.setSize(new Dimension(200, 50));
        this.thirdName = new JTextField("", 10);
        this.thirdName.setSize(new Dimension(200, 50));
        this.firstNameLab = new JLabel(StringResources.MAIN.getString("first_player_name") + ": ");
        this.secondNameLab = new JLabel(StringResources.MAIN.getString("second_player_name") + ": ");
        this.thirdNameLab = new JLabel(StringResources.MAIN.getString("third_player_name") + ": ");

        this.setLayout(gbl);
        this.okButton.addActionListener(this);

        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.gbl.setConstraints(firstNameLab, gbc);
        this.add(firstNameLab);
        this.gbc.gridy = 1;
        this.gbl.setConstraints(firstName, gbc);
        this.add(firstName);
        this.gbc.gridy = 2;
        this.gbl.setConstraints(secondNameLab, gbc);
        this.add(secondNameLab);
        this.gbc.gridy = 3;
        this.gbl.setConstraints(secondName, gbc);
        this.add(secondName);
        this.gbc.gridy = 4;
        this.gbl.setConstraints(thirdNameLab, gbc);
        this.add(thirdNameLab);
        this.gbc.gridy = 5;
        this.gbl.setConstraints(thirdName, gbc);
        this.add(thirdName);
        this.gbc.gridy = 6;
        this.gbl.setConstraints(okButton, gbc);
        this.add(okButton);
    }

    /**
     * Method witch is checking correction of edit tables
     *
     * @param e Object where is saving this what contents edit tables
     */
    public void textValueChanged(TextEvent e) {
        Object target = e.getSource();
        if (target == this.firstName || target == this.secondName) {
            JTextField temp = new JTextField();
            if (target == this.firstName) {
                temp = this.firstName;
            } else if (target == this.secondName) {
                temp = this.secondName;
            }

            int len = temp.getText().length();
            if (len > 8) {
                try {
                    temp.setText(temp.getText(0, 7));
                } catch (BadLocationException exc) {
                    System.out.println("Something wrong in editables: \n" + exc);
                }
            }
        }
    }

    /**
     * Method responsible for changing the options which can make a player
     * when he want to start new local game
     *
     * @param e where is saving data of performed action
     */
    public void actionPerformed(ActionEvent e) {
        Object target = e.getSource();
        if (target == this.okButton) { //if clicked OK button (on finish)
            if (this.firstName.getText().length() > 9) {
                //make names short to 10 digits
                this.firstName.setText(this.trimString(firstName, 9));
            }
            if (this.secondName.getText().length() > 9) {
                //make names short to 10 digits
                this.secondName.setText(this.trimString(secondName, 9));
            }
            if (this.thirdName.getText().length() > 9) {
                //make names short to 10 digits
                this.thirdName.setText(this.trimString(thirdName, 9));
            }


            /* new method call from apfohl */
            parPtr.createNewGame(this.firstName.getText(),
                    this.secondName.getText(),
                    this.thirdName.getText());
        }

    }

    /**
     * Method responsible for triming white symbols from strings
     *
     * @param txt    Where is capt value to equal
     * @param length How long is the strings
     * @return result trimmed String
     */
    String trimString(JTextField txt, int length) {
        String result = "";
        try {
            result = txt.getText(0, length);
        } catch (BadLocationException exc) {
            System.out.println("Something wrong in editables: \n" + exc);
        }
        return result;
    }
}