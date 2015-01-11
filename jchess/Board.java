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

    /** Access a Tile of a board by axial coordinates as explained in the following article:
     * http://www.redblobgames.com/grids/hexagons/
     * the Tiles are ordered differently due to inner workings of the picking system
     * Tile( 0, 0 ) is the top most tile
     * Tile( 1, 0 ) is left bellow
     * Tile( 0, 1 ) is right bellow the top most tile and so forth
     * the third cubical coordinate can be computed with b - a, while for this one negative values are valid
     * All the Tiles on the center column have the cubical coordinate c = 0
     * the columns to the left are negative, and to the right are positive
     * @param a first axial coordinate
     * @param b second axial coordinate
     */
    public Tile getTile( int a, int b ) {
         return tiles[ CoordinateConverter.boardAxialCoordinateToIndex( a, b ) ];
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

        for( int b = 0 ; b < 4 ; ++b ) tiles[CoordinateConverter.boardAxialCoordinateToIndex(1,b)].placePiece(new Pawn(0));
        for( int b = 3 ; b < 5 ; ++b ) tiles[CoordinateConverter.boardAxialCoordinateToIndex(2,b)].placePiece(new Pawn(0));
        for( int b = 5 ; b < 9 ; ++b ) tiles[CoordinateConverter.boardAxialCoordinateToIndex(1,b)].placePiece(new Pawn(0));



        tiles[147].placePiece( new Pawn(1) );
        tiles[109].placePiece( new Pawn(1) );
        tiles[110].placePiece( new Pawn(1) );
        tiles[111].placePiece( new Pawn(1) );
        tiles[112].placePiece(new Pawn(1));
        tiles[113].placePiece(new Pawn(1));
        tiles[114].placePiece( new Pawn(1) );
        tiles[115].placePiece( new Pawn(1) );
        tiles[109].placePiece( new Pawn(1) );
        tiles[156].placePiece( new Pawn(1) );
        tiles[78].placePiece(new Pawn(1));
        tiles[79].placePiece(new Pawn(1));

        tiles[133].placePiece( new Pawn(2) );
        tiles[97].placePiece(new Pawn(2));
        tiles[98].placePiece(new Pawn(2));
        tiles[99].placePiece(new Pawn(2));
        tiles[100].placePiece(new Pawn(2));
        tiles[101].placePiece(new Pawn(2));
        tiles[102].placePiece(new Pawn(2));
        tiles[103].placePiece(new Pawn(2));
        tiles[142].placePiece( new Pawn(2) );
        tiles[68].placePiece(new Pawn(2));
        tiles[69].placePiece( new Pawn(2) );

        // place Rooks
        tiles[127].placePiece( new Rook(0) ) ;
        tiles[162].placePiece( new Rook(0) ) ;

        tiles[148].placePiece( new Rook(1) ) ;
        tiles[155].placePiece( new Rook(1) ) ;

        tiles[134].placePiece( new Rook(2) ) ;
        tiles[141].placePiece( new Rook(2) ) ;

        // place Bishops
        tiles[163].placePiece( new Bishop(0) ) ;
        tiles[168].placePiece( new Bishop(0) ) ;
        tiles[124].placePiece( new Bishop(0) ) ;

        tiles[149].placePiece( new Bishop(1) ) ;
        tiles[154].placePiece( new Bishop(1) ) ;
        tiles[112].placePiece( new Bishop(1) ) ;

        tiles[135].placePiece( new Bishop(2) ) ;
        tiles[140].placePiece( new Bishop(2) ) ;
        tiles[100].placePiece( new Bishop(2) ) ;

        // place Knights
        tiles[164].placePiece( new Knight(0) ) ;
        tiles[167].placePiece( new Knight(0) ) ;

        tiles[150].placePiece( new Knight(1) ) ;
        tiles[153].placePiece( new Knight(1) ) ;

        tiles[136].placePiece( new Knight(2) ) ;
        tiles[139].placePiece( new Knight(2) ) ;

        // place Queens
        tiles[165].placePiece( new Queen(0) ) ;
        tiles[151].placePiece( new Queen(1) ) ;
        tiles[137].placePiece( new Queen(2) ) ;

        // place Kings
        tiles[166].placePiece( new King(0) ) ;
        tiles[152].placePiece( new King(1) ) ;
        tiles[138].placePiece( new King(2) ) ;
    }
}

