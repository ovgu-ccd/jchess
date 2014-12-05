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

/**
 * Class to represent a chessboard square
 */
public class Square {

    private int pozX; // 0-7, becouse 8 squares for row/column
    private int pozY; // 0-7, becouse 8 squares for row/column
    public Piece piece = null;//object Piece on square (and extending Piecie)

    public Square(int pozX, int pozY, Piece piece) {
        this.setPozX(pozX);
        this.setPozY(pozY);
        this.piece = piece;
    }/*--endOf-Square--*/


    public Square(Square square) {
        this.setPozX(square.getPozX());
        this.setPozY(square.getPozY());
        this.piece = square.piece;
    }

    public Square clone(Square square) {
        return new Square(square);
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        this.piece.square = this;
    }

    public int getPozX() {
        return pozX;
    }

    public void setPozX(int pozX) {
        this.pozX = pozX;
    }

    public int getPozY() {
        return pozY;
    }

    public void setPozY(int pozY) {
        this.pozY = pozY;
    }
}
