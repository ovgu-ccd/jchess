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

import jchess.util.StringResources;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Class responsible for drawing the fold with local game settings
 */
class DrawLocalSettings extends JPanel implements ActionListener {
    private final NewGameDialog parent;
    private final JTextField firstName;
    private final JTextField secondName;
    private final JTextField thirdName;

    private final JButton okButton;


    public DrawLocalSettings(NewGameDialog parent) {
        super();
        this.parent = parent;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel firstPanel = new JPanel();
        firstPanel.setBorder(BorderFactory.createTitledBorder(StringResources.MAIN.getString("player") + " 1"));
        JLabel firstNameLab = new JLabel(StringResources.MAIN.getString("player_name") + ": ");
        this.firstName = new JTextField("Player 1", 10);
        firstPanel.add(firstNameLab);
        firstPanel.add(firstName);


        JPanel secondPanel = new JPanel();
        secondPanel.setBorder(BorderFactory.createTitledBorder(StringResources.MAIN.getString("player") + " 2"));
        JLabel secondNameLab = new JLabel(StringResources.MAIN.getString("player_name") + ": ");
        this.secondName = new JTextField("Player 2", 10);
        secondPanel.add(secondNameLab);
        secondPanel.add(secondName);

        JPanel thirdPanel = new JPanel();
        thirdPanel.setBorder(BorderFactory.createTitledBorder(StringResources.MAIN.getString("player") + " 3"));
        JLabel thirdNameLab = new JLabel(StringResources.MAIN.getString("player_name") + ": ");
        this.thirdName = new JTextField("Player 3", 10);
        thirdPanel.add(thirdNameLab);
        thirdPanel.add(thirdName);

        this.okButton = new JButton(StringResources.MAIN.getString("ok"));
        this.okButton.addActionListener(this);


        this.add(firstPanel);
        this.add(secondPanel);
        this.add(thirdPanel);

        this.add(okButton);
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
                this.firstName.setText(this.trimString(firstName));
            }
            if (this.secondName.getText().length() > 9) {
                //make names short to 10 digits
                this.secondName.setText(this.trimString(secondName));
            }
            if (this.thirdName.getText().length() > 9) {
                //make names short to 10 digits
                this.thirdName.setText(this.trimString(thirdName));
            }


            /* new method call from apfohl */
            parent.createNewGame(this.firstName.getText(),
                    this.secondName.getText(),
                    this.thirdName.getText());
        }

    }

    /**
     * Method responsible for triming WHITE symbols from strings
     *
     * @param txt Where is capt value to equal
     * @return result trimmed String
     */
    String trimString(JTextField txt) {
        String result = "";
        try {
            result = txt.getText(0, 9);
        } catch (BadLocationException exc) {
            System.out.println("Something wrong in editables: \n" + exc);
        }
        return result;
    }
}