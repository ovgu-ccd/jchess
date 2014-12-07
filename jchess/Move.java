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
 * Author: Mateusz Sławomir Lach ( matlak, msl )
 */
package jchess;

import jchess.Moves.castling;
import jchess.gui.BoardView;

public class Move {

    private Square   from                 = null;
    private Square   to                   = null;
    private Piece    movedPiece           = null;
    private Piece    takenPiece           = null;
    private Piece    promotedTo           = null;
    private boolean  wasEnPassant         = false;
    private castling castlingMove         = castling.none;
    private boolean  wasPawnTwoFieldsMove = false;


    Move(Square from, Square to, Piece movedPiece, Piece takenPiece, castling castlingMove, boolean wasEnPassant,
            Piece promotedPiece) {
        this.from = from;
        this.to = to;

        this.movedPiece = movedPiece;
        this.takenPiece = takenPiece;

        this.castlingMove = castlingMove;
        this.wasEnPassant = wasEnPassant;

        if (movedPiece.getName().equals("Pawn") && Math.abs(to.getPozY() - from.getPozY()) == 2) {
            this.wasPawnTwoFieldsMove = true;
        } else if (movedPiece.getName().equals("Pawn") && to.getPozY() == BoardView.bottom
                || to.getPozY() == BoardView.top && promotedPiece != null) {
            this.promotedTo = promotedPiece;
        }
    }


    public Square getFrom() {
        return this.from;
    }


    public Square getTo() {
        return this.to;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public Piece getTakenPiece() {
        return this.takenPiece;
    }

    public boolean wasEnPassant() {
        return this.wasEnPassant;
    }

    public boolean wasPawnTwoFieldsMove() {
        return this.wasPawnTwoFieldsMove;
    }

    public castling getCastlingMove() {
        return this.castlingMove;
    }

    public Piece getPromotedPiece() {
        return this.promotedTo;
    }
}
