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
 * Damian Marciniak
 */
package jchess.gui;

import jchess.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Class responsible for the starts of new games, loading games,
 * saving it, and for ending it.
 * This class is also responsible for appoing player with have
 * a move at the moment
 */
public class GameTab extends JPanel {

    public Settings settings;
    public BoardView boardView;
    public Client client;
    public Chat chat;


    public GameTab() {
        this.setLayout(null);
        settings = new Settings();
        boardView = new BoardView(this.settings);
        boardView.setVisible(true);
        boardView.setSize(boardView.getWidth(), boardView.getHeight());
        boardView.setLocation(new Point(0, 0));
        this.add(boardView);

        this.chat = new Chat();
        this.chat.setSize(new Dimension(380, 100));
        this.chat.setLocation(new Point(0, 500));
        this.chat.setMinimumSize(new Dimension(400, 100));

        this.setLayout(null);
        this.setDoubleBuffered(true);
    }


}