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
package jchess.game.board;

import jchess.game.pieces.*;

/**
 * Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squares of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 */
public class DefaultBoard extends Board {

    /// Initial Figure Placement
    protected void initFigures() {

        try {
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
        } catch (InvalidBoardCoordinateException e) {
            e.printStackTrace();
        }
    }
}