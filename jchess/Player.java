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

    private String name;
    private IOSystem ioSystem;
    private boolean active;

    public Player(String name, IOSystem ioSystem, String color) {
        this.setName(name);
        this.setColor(colors.valueOf(color));
        this.ioSystem = ioSystem;
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public colors getColor() {
        return color;
    }

    void setColor(colors color) {
        this.color = color;
    }

    public enum colors {
        white,
        black,
        red
    }

    private colors color;

    public enum playerTypes {
        localUser, networkUser, computer
    }

    public playerTypes playerType;

    /**
     * Method setting the players name
     *
     * @param name name of player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method getting the players name
     *
     * @return name of player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method setting the players type
     *
     * @param type type of player - enumerate
     */
    public void setType(playerTypes type) {
        this.playerType = type;
    }

    public IOSystem getIoSystem() {
        return ioSystem;
    }
}
