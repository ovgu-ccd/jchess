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
package jchess.game.pieces;


import jchess.game.board.TileFilter;
import jchess.util.BoardCoordinate;

/**
 * Moves on straight edges forward and backward
 *
 * @trace [$REQ13b]
 * @trace [$REQ15]
 */
public class Bishop extends Piece {

    public Bishop(int playerID) {
        super(playerID);
        BoardCoordinate empty[] = {};
        BoardCoordinate repeat[] = {
                new BoardCoordinate(1, -1), new BoardCoordinate(2, 1), new BoardCoordinate(1, 2),
                new BoardCoordinate(-1, 1), new BoardCoordinate(-2, -1), new BoardCoordinate(-1, -2)
        };
        tileFilter = new TileFilter(repeat, empty, empty);
    }
}
