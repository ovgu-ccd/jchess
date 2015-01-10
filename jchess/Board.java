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
//import java.lang.IllegalArgumentException;


import jchess.pieces.Pawn;

/** Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squers of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 */
public class Board {

    private Tile tiles[];

    public Board() {

        // generate Fields
        tiles = new Tile[ 1 + 1*6 + 2*6 * 3*6 + 4*6 + 5*6 + 6*6 + 7*6 ] ;
        tiles[168].placePiece( new Pawn() );
        tiles[ 91].placePiece( new Pawn() );
        tiles[ 92].placePiece( new Pawn() );
        tiles[ 93].placePiece( new Pawn() );
        tiles[ 63].placePiece( new Pawn() );
        tiles[ 64].placePiece( new Pawn() );
        tiles[ 95].placePiece( new Pawn() );
        tiles[ 96].placePiece( new Pawn() );
        tiles[ 97].placePiece( new Pawn() );
        tiles[135].placePiece( new Pawn() );

        tiles[140].placePiece( new Pawn() );
        tiles[103].placePiece( new Pawn() );
        tiles[104].placePiece( new Pawn() );
        tiles[105].placePiece( new Pawn() );
        tiles[ 73].placePiece( new Pawn() );
        tiles[ 74].placePiece( new Pawn() );
        tiles[107].placePiece( new Pawn() );
        tiles[108].placePiece( new Pawn() );
        tiles[109].placePiece( new Pawn() );
        tiles[149].placePiece( new Pawn() );

        tiles[154].placePiece( new Pawn() );
        tiles[115].placePiece( new Pawn() );
        tiles[116].placePiece( new Pawn() );
        tiles[117].placePiece( new Pawn() );
        tiles[ 83].placePiece( new Pawn() );
        tiles[ 84].placePiece( new Pawn() );
        tiles[119].placePiece( new Pawn() );
        tiles[120].placePiece( new Pawn() );
        tiles[121].placePiece( new Pawn() );
        tiles[163].placePiece( new Pawn() );

    }/*--endOf-Chessboard--*/


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

    /** Access a Tile of a board
     * The Tiles are arranged in rings around the center tile
     * The Center Tile has ringIndex = 0, tileOnRingIndex 0
     * The first Tile on a ring is north (above) of the first Tile of the predecessor ring
     * Tiles are counted in clockwise order
     * @param ringIndex Index for the ring, starting at 0 with the center tile/ring
     * @param tileOnRingIndex Index for a tile on the ring, starting with zero for the central northern tile, cw order
     */
    public Tile getTile( int ringIndex, int tileOnRingIndex ) {

        if ( ringIndex < 0 || ringIndex > 7 )
            throw new IllegalArgumentException("Parameter ringIndex must be in the interval [0..7] !");

        if ( tileOnRingIndex < 0 || tileOnRingIndex > ringIndex * 6 - 1 )
            throw new IllegalArgumentException("Parameter tileOnRingIndex must be in the interval [0..ringIndex * 6 - 1] !");

        // Compute board tile index from ringIndex and tileOnRingIndex
        int tileIndex = 0;

        // Sum up all tiles from Ring Index 0 to Ring Index ringIndex-1 and add tileOnRingIndex
        // Using Gauss' Sum Formula
        if (ringIndex > 0) {
            ringIndex -= 1;
            tileIndex = (ringIndex * ringIndex + ringIndex) / 2 + tileOnRingIndex;
        }

        return tiles[tileIndex];
    }

    public Tile getTile( int tileIndex ) {
        return tiles[tileIndex];
    }

    public boolean undo() {
        return true;
    }

    public boolean redo() {
        return false;
    }
    
}

