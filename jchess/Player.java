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
package jchess;

import java.io.Serializable;


/**
 * Class representing the player in the game
 */
public class Player implements Serializable {

    public enum PlayerColor {WHITE, BLACK, RED}
    public enum PlayerType {LOCAL, NETWORK, COMPUTER}

    private String name;
    private Game game;
    private IOSystem ioSystem;
    private boolean active;
    private PlayerType playerType;
    private PlayerColor color;


    public Player(String name, IOSystem ioSystem, PlayerColor color) {
        this.setName(name);
        this.setColor(color);
        this.ioSystem = ioSystem;
        this.active = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public IOSystem getIoSystem() {
        return ioSystem;
    }

    public void setIoSystem(IOSystem ioSystem) {
        this.ioSystem = ioSystem;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }
}
