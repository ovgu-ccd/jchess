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

import jchess.pieces.*;
import jchess.util.CoordinateConverter;

/** Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squers of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 */
public class Board {

    private Tile tiles[];

    public Board() {
        // generate Fields

        tiles = new Tile[ 1 + 1*6 + 2*6 + 3*6 + 4*6 + 5*6 + 6*6 + 7*6 ] ;

        for (int i = 0; i< tiles.length; i++){
            tiles[i] = new Tile();
        }

        initNeighborhood();
        initFigures();
    }


    Tile[] filterTiles( TileFilter filter ) {
        return tiles;
    }

    /// handle the move from one field to another
    void handleMove( Move move ) {

        if ( move.getMovedPiece() != move.getFrom().getPiece() )
            throw new IllegalArgumentException("The moved piece is not on the move from field !");

        if ( move.getTakenPiece() != move.getTo().getPiece() )
            throw new IllegalArgumentException("The taken piece is not on the move to field !");

        move.getFrom().removePiece();
        move.getTo().placePiece(move.getMovedPiece());
    }

    /** Access a Tile of a board by absolute index
     * Tile index starts in the board center
     * next tile is above and following tiles are on the same ring
     * next tile is above and so forth ...
     * @param tileIndex Index for the ring, starting at 0 with the center tile/ring
     */
    public Tile getTile( int tileIndex ) {
        return tiles[tileIndex];
    }

    public int getNumTiles(){
        return tiles.length;
    }

    public boolean undo() {
        return true;
    }

    public boolean redo() {
        return false;
    }

    /// Initialize Neighborhood relationships
    void initNeighborhood() {

    }
      /// Initial Figure Placement
    void initFigures() {

        // place Pawns

        for( int b = 0 ; b < 4 ; ++b ) tiles[CoordinateConverter.boardCoordinateToIndex(1, b)].placePiece(new Pawn(0));
        for( int b = 3 ; b < 5 ; ++b ) tiles[CoordinateConverter.boardCoordinateToIndex(2, b)].placePiece(new Pawn(0));
        for( int b = 5 ; b < 9 ; ++b ) tiles[CoordinateConverter.boardCoordinateToIndex(1, b)].placePiece(new Pawn(0));

        tiles[147].placePiece( new Pawn(0) );
    }
}

