/**
 * Created by Elliot on 2014-12-06.
 */
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

//import jchess.Moves.castling;




/** Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squers of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 */
public class Board {

    private Tile tiles[];

    /** Chessboard class constructor
     * @param settings reference to Settings class object for this chessboard
     * @param moves_history reference to Moves class object for this chessboard
     */
    //public Chessboard(Settings settings, Moves moves_history) {
    //this.settings = settings;
    //this.moves_history = moves_history;

    public Board() {

        // generate Fields
        tiles = new Tile[ 1 + 2*6 * 3*6 + 4*6 + 5*6 + 6*6 + 7*6 ] ;

    }/*--endOf-Chessboard--*/


    Tile[] filterTiles( TileFilter filter ) {
        return tiles;
    }

    /// handle the move from one fi
    void handleMove( Move move ) {

    }

    /** Access a Tile of a board
     * The Tiles are arranged in rings around the center tile
     * The Center Tile has ringIndex = 0, tileOnRingIndex 0
     * The first Tile on a ring is north (above) of the first Tile of the predecessor ring
     * Tiles are counted in clockwise order
     * @param ringIndex Index for the ring, starting at 0 with the center tile/ring
     * @param tileOnRingIndex Index for a tile on the ring, starting with zero for the central northern tile, cw order
     */
    Tile getTile( int ringIndex, int tileOnRingIndex ) {
        return tiles[0];
    }

    boolean undo() {
        return true;
    }

    boolean redo() {
        return false;
    }


}

