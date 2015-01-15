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
import jchess.util.BoardCoordinate;
import jchess.util.CoordinateConverter;

/**
 * Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squers of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 */
public class Board {

    private Tile tiles[];

    public Board() {
        // generate Fields

        tiles = new Tile[1 + 1 * 6 + 2 * 6 + 3 * 6 + 4 * 6 + 5 * 6 + 6 * 6 + 7 * 6];

        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile();
        }

        initNeighborhood();
        initFigures();
    }


    Tile[] filterTiles(TileFilter filter) {
        return tiles;
    }

    /**
     * Access a Tile of a board by absolute index
     * Tile index starts in the board center
     * next tile is above and following tiles are on the same ring
     * next tile is above and so forth ...
     *
     * @param tileIndex Index for the ring, starting at 0 with the center tile/ring
     */
    public Tile getTile(int tileIndex) {
        return tiles[tileIndex];

    }

    /**
     * Access a Tile of a board by axial coordinates as explained in the following article:
     * http://www.redblobgames.com/grids/hexagons/
     * the Tiles are ordered differently due to inner workings of the picking system
     * Tile( 0, 0 ) is the top most tile
     * Tile( 1, 0 ) is left bellow
     * Tile( 0, 1 ) is right bellow the top most tile and so forth
     * the third cubical coordinate can be computed with b - a, while for this one negative values are valid
     * All the Tiles on the center column have the cubical coordinate c = 0
     * the columns to the left are negative, and to the right are positive
     *
     * @param a first axial coordinate
     * @param b second axial coordinate
     */
    public Tile getTile(int a, int b) {
        return tiles[CoordinateConverter.boardCoordinateToIndex(a, b)];
    }

    /**
     * Access a Tile of a board by a BoardCoordinate Class
     *
     * @param boardCoordinate well ...
     */
    public Tile getTile(BoardCoordinate boardCoordinate) {
        return getTile(boardCoordinate.getI());
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
        for (int b = 0; b < 4; ++b) getTile(1, b).placePiece(new Pawn(0));
        for (int b = 4; b < 6; ++b) getTile(2, b).placePiece(new Pawn(0));
        for (int b = 5; b < 9; ++b) getTile(1, b).placePiece(new Pawn(0));

        for (int a = 6; a < 10; ++a) getTile(a, 13).placePiece(new Pawn(1));
        for (int a = 9; a < 11; ++a) getTile(a, 12).placePiece(new Pawn(1));
        for (int a = 11; a < 15; ++a) getTile(a, 13).placePiece(new Pawn(1));

        for (int b = 0; b < 4; ++b) getTile(6 + b, b).placePiece(new Pawn(2));
        for (int b = 4; b < 6; ++b) getTile(5 + b, b).placePiece(new Pawn(2));
        for (int b = 5; b < 9; ++b) getTile(6 + b, b).placePiece(new Pawn(2));

        // place Rooks
        getTile(0, 0).placePiece(new Rook(0));
        getTile(0, 0).addPromotionForPlayer(1);
        getTile(0, 0).addPromotionForPlayer(2);
        getTile(0, 7).placePiece(new Rook(0));
        getTile(0, 7).addPromotionForPlayer(1);
        getTile(0, 7).addPromotionForPlayer(2);

        getTile(7, 14).placePiece(new Rook(1));
        getTile(7, 14).addPromotionForPlayer(0);
        getTile(7, 14).addPromotionForPlayer(2);
        getTile(14, 14).placePiece(new Rook(1));
        getTile(14, 14).addPromotionForPlayer(0);
        getTile(14, 14).addPromotionForPlayer(2);

        getTile(14, 7).placePiece(new Rook(2));
        getTile(14, 7).addPromotionForPlayer(0);
        getTile(14, 7).addPromotionForPlayer(1);
        getTile(7, 0).placePiece(new Rook(2));
        getTile(7, 0).addPromotionForPlayer(0);
        getTile(7, 0).addPromotionForPlayer(1);

        // place Bishops
        getTile(0, 1).placePiece(new Bishop(0));
        getTile(0, 1).addPromotionForPlayer(1);
        getTile(0, 1).addPromotionForPlayer(2);
        getTile(1, 4).placePiece(new Bishop(0));
        getTile(0, 6).placePiece(new Bishop(0));
        getTile(0, 6).addPromotionForPlayer(1);
        getTile(0, 6).addPromotionForPlayer(2);

        getTile(8, 14).placePiece(new Bishop(1));
        getTile(8, 14).addPromotionForPlayer(0);
        getTile(8, 14).addPromotionForPlayer(2);
        getTile(10, 13).placePiece(new Bishop(1));
        getTile(13, 14).placePiece(new Bishop(1));
        getTile(13, 14).addPromotionForPlayer(0);
        getTile(13, 14).addPromotionForPlayer(2);

        getTile(13, 6).placePiece(new Bishop(2));
        getTile(13, 6).addPromotionForPlayer(0);
        getTile(13, 6).addPromotionForPlayer(1);
        getTile(10, 4).placePiece(new Bishop(2));
        getTile(8, 1).placePiece(new Bishop(2));
        getTile(8, 1).addPromotionForPlayer(0);
        getTile(8, 1).addPromotionForPlayer(1);

        // place Knights
        getTile(0, 2).placePiece(new Knight(0));
        getTile(0, 2).addPromotionForPlayer(1);
        getTile(0, 2).addPromotionForPlayer(2);
        getTile(0, 5).placePiece(new Knight(0));
        getTile(0, 5).addPromotionForPlayer(1);
        getTile(0, 5).addPromotionForPlayer(2);

        getTile(9, 14).placePiece(new Knight(1));
        getTile(9, 14).addPromotionForPlayer(0);
        getTile(9, 14).addPromotionForPlayer(2);
        getTile(12, 14).placePiece(new Knight(1));
        getTile(12, 14).addPromotionForPlayer(0);
        getTile(12, 14).addPromotionForPlayer(2);

        getTile(12, 5).placePiece(new Knight(2));
        getTile(12, 5).addPromotionForPlayer(0);
        getTile(12, 5).addPromotionForPlayer(1);
        getTile(9, 2).placePiece(new Knight(2));
        getTile(9, 2).addPromotionForPlayer(0);
        getTile(9, 2).addPromotionForPlayer(1);

        // place Queens
        getTile(0, 3).placePiece(new Queen(0));
        getTile(0, 3).addPromotionForPlayer(1);
        getTile(0, 3).addPromotionForPlayer(2);
        getTile(10, 14).placePiece(new Queen(1));
        getTile(10, 14).addPromotionForPlayer(0);
        getTile(10, 14).addPromotionForPlayer(2);
        getTile(11, 4).placePiece(new Queen(2));
        getTile(11, 4).addPromotionForPlayer(0);
        getTile(11, 4).addPromotionForPlayer(1);

        // place Kings
        getTile(0, 4).placePiece(new King(0));
        getTile(0, 4).addPromotionForPlayer(1);
        getTile(0, 4).addPromotionForPlayer(2);
        getTile(11, 14).placePiece(new King(1));
        getTile(11, 14).addPromotionForPlayer(0);
        getTile(11, 14).addPromotionForPlayer(2);
        getTile(10, 3).placePiece(new King(2));
        getTile(10, 3).addPromotionForPlayer(0);
        getTile(10, 3).addPromotionForPlayer(1);
    }
}