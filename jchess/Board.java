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

/** Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squers of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 */
public class Board {

    private Tile tiles[];

    public Board() {

        // generate Fields
        tiles = new Tile[ 1 + 1*6 + 2*6 * 3*6 + 4*6 + 5*6 + 6*6 + 7*6 ] ;
        initTiles();
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
    /** Access a Tile of a board by absolute index
     * Tile index starts in the board center
     * next tile is above and following tiles are on the same ring
     * next tile is above and so forth ...
     * @param tileIndex Index for the ring, starting at 0 with the center tile/ring
     */
    public Tile getTile( int tileIndex ) {
        return tiles[tileIndex];
    }

    public boolean undo() {
        return true;
    }

    public boolean redo() {
        return false;
    }

    void initTiles() {
        tiles[0].setNeigbors( new Tile[]{ tiles[1], tiles[2], tiles[3], tiles[4], tiles[5], tiles[6] } );


    }

    /// Initial Figure Placement
    void initFigures() {
        // place Pawns
        tiles[168].placePiece( new Pawn(0) );
        tiles[ 91].placePiece( new Pawn(0) );
        tiles[ 92].placePiece( new Pawn(0) );
        tiles[ 93].placePiece( new Pawn(0) );
        tiles[ 63].placePiece( new Pawn(0) );
        tiles[ 64].placePiece( new Pawn(0) );
        tiles[ 95].placePiece( new Pawn(0) );
        tiles[ 96].placePiece( new Pawn(0) );
        tiles[ 97].placePiece( new Pawn(0) );
        tiles[135].placePiece( new Pawn(0) );

        tiles[140].placePiece( new Pawn(1) );
        tiles[103].placePiece( new Pawn(1) );
        tiles[104].placePiece( new Pawn(1) );
        tiles[105].placePiece( new Pawn(1) );
        tiles[ 73].placePiece( new Pawn(1) );
        tiles[ 74].placePiece( new Pawn(1) );
        tiles[107].placePiece( new Pawn(1) );
        tiles[108].placePiece( new Pawn(1) );
        tiles[109].placePiece( new Pawn(1) );
        tiles[149].placePiece( new Pawn(1) );

        tiles[154].placePiece( new Pawn(2) );
        tiles[115].placePiece( new Pawn(2) );
        tiles[116].placePiece( new Pawn(2) );
        tiles[117].placePiece( new Pawn(2) );
        tiles[ 83].placePiece( new Pawn(2) );
        tiles[ 84].placePiece( new Pawn(2) );
        tiles[119].placePiece( new Pawn(2) );
        tiles[120].placePiece( new Pawn(2) );
        tiles[121].placePiece( new Pawn(2) );
        tiles[163].placePiece( new Pawn(2) );

        // place Rooks
        tiles[127].placePiece( new Rook(0) ) ;
        tiles[134].placePiece( new Rook(0) ) ;
        tiles[141].placePiece( new Rook(1) ) ;
        tiles[148].placePiece( new Rook(1) ) ;
        tiles[155].placePiece( new Rook(2) ) ;
        tiles[162].placePiece( new Rook(2) ) ;

        // place Bishops
        tiles[135].placePiece( new Bishop(0) ) ;
        tiles[100].placePiece( new Bishop(0) ) ;
        tiles[140].placePiece( new Bishop(0) ) ;
        tiles[149].placePiece( new Bishop(1) ) ;
        tiles[112].placePiece( new Bishop(1) ) ;
        tiles[154].placePiece( new Bishop(1) ) ;
        tiles[163].placePiece( new Bishop(2) ) ;
        tiles[124].placePiece( new Bishop(2) ) ;
        tiles[168].placePiece( new Bishop(2) ) ;

        // place Knights
        tiles[136].placePiece( new Knight(0) ) ;
        tiles[139].placePiece( new Knight(0) ) ;
        tiles[150].placePiece( new Knight(1) ) ;
        tiles[153].placePiece( new Knight(1) ) ;
        tiles[164].placePiece( new Knight(2) ) ;
        tiles[167].placePiece( new Knight(2) ) ;

        // place Queens
        tiles[137].placePiece( new Queen(0) ) ;
        tiles[151].placePiece( new Queen(1) ) ;
        tiles[165].placePiece( new Queen(2) ) ;

        // place Kings
        tiles[138].placePiece( new King(0) ) ;
        tiles[152].placePiece( new King(1) ) ;
        tiles[166].placePiece( new King(2) ) ;
    }
    
}

