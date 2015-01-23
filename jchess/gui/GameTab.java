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

import jchess.eventbus.Controller;
import jchess.game.Game;

import javax.swing.*;

/**
 * Class responsible for the starts of new games, loading games,
 * saving it, and for ending it.
 * This class is also responsible for appoing player with have
 * a move at the moment
 */
public class GameTab extends JPanel {

    public BoardView boardView;
    private Game game;

    public GameTab() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        boardView = new BoardView();

        add(Box.createHorizontalGlue());
        add(boardView);
        add(Box.createHorizontalGlue());

        Controller.INSTANCE.subscribe(this);
    }


    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}