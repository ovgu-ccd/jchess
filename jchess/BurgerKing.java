package jchess;

import jchess.gui.BoardView;

import java.util.ArrayList;

public class BurgerKing extends King {
    public BurgerKing(BoardView boardView, Player player) {
        super(boardView, player);
    }

    /*@Override
    public ArrayList<Square> allMoves() {
    	ArrayList<Square> moves = new ArrayList<Square>();
        Square sq;
        Square sq1;

    	ArrayList<Square> squares = new ArrayList<Square>();
        squares.add(this.chessboard.squares[this.square.pozX + 1][this.square.pozY]);
        squares.add(this.chessboard.squares[this.square.pozX - 1][this.square.pozY]);
        squares.add(this.chessboard.squares[this.square.pozX][this.square.pozY + 1]);
        squares.add(this.chessboard.squares[this.square.pozX][this.square.pozY - 1]);

        for (Square square : squares) {
        	sq = square;
        	if (!this.isout(square.pozX, square.pozY))
        	{//out of bounds protection
        		sq = square;
        	}
        	if (this.checkPiece(square.pozX, square.pozY)) {
    			if (isSafe(sq)) {
    				moves.add(sq);
    			}
    		}
    	}

        if (!this.wasMotion && !this.isChecked())
        {//check if king was not moved before


            if (chessboard.squares[0][this.square.pozY].piece != null
                    && chessboard.squares[0][this.square.pozY].piece.name.equals("Rook"))
            {
                boolean canCastling = true;

                Rook rook = (Rook) chessboard.squares[0][this.square.pozY].piece;
                if (!rook.wasMotion)
                {
                    for (int i = this.square.pozX - 1; i > 0; i--)
                    {//go left
                        if (chessboard.squares[i][this.square.pozY].piece != null)
                        {
                            canCastling = false;
                            break;
                        }
                    }
                    sq = this.chessboard.squares[this.square.pozX - 2][this.square.pozY];
                    sq1 = this.chessboard.squares[this.square.pozX - 1][this.square.pozY];
                    if (canCastling && this.isSafe(sq) && this.isSafe(sq1))
                    { //can do castling when none of Sq,sq1 is checked
                        moves.add(sq);
                    }
                }
            }
            if (chessboard.squares[7][this.square.pozY].piece != null
                    && chessboard.squares[7][this.square.pozY].piece.name.equals("Rook"))
            {
                boolean canCastling = true;
                Rook rook = (Rook) chessboard.squares[7][this.square.pozY].piece;
                if (!rook.wasMotion)
                {//if king was not moves before and is not checked
                    for (int i = this.square.pozX + 1; i < 7; i++)
                    {//go right
                        if (chessboard.squares[i][this.square.pozY].piece != null)
                        {//if square is not empty
                            canCastling = false;//cannot castling
                            break; // exit
                        }
                    }
                    sq = this.chessboard.squares[this.square.pozX + 2][this.square.pozY];
                    sq1 = this.chessboard.squares[this.square.pozX + 1][this.square.pozY];
                    if (canCastling && this.isSafe(sq) && this.isSafe(sq1))
                    {//can do castling when none of Sq,sq1 is checked
                        moves.add(sq);
                    }
                }
            }
        }

        return moves;
    }*/

    @Override
    public ArrayList allMoves() {
        ArrayList list = new ArrayList();
        Square sq;
        Square sq1;
        for (int i = this.square.getPozX() - 1; i <= this.square.getPozX() + 1; i++) {
            for (int y = this.square.getPozY() - 1; y <= this.square.getPozY() + 1; y++) {
                if (!this.isout(i, y)) {
                    //out of bounds protection
                    sq = this.getBoardView().squares[i][y];
                    if (this.square == sq) {
                        //if we're checking square on which is King
                        continue;
                    }
                    if (this.checkPiece(i, y)) {
                        //if square is empty
                        if (isSafe(sq)) {
                            if (sq.getPozX() == this.square.getPozX() || sq.getPozY() == this.square.getPozY()) {
                                list.add(sq);
                            }
                        }
                    }
                }
            }
        }

        if (!this.wasMotion && !this.isChecked()) {
            //check if king was not moved before


            if (getBoardView().squares[0][this.square.getPozY()].piece != null
                    && (getBoardView().squares[0][this.square.getPozY()].piece.getName().equals("Rook") || getBoardView().squares[0][this.square.getPozY()].piece.getName().equals("SpecialRook"))) {
                boolean canCastling = true;

                Rook rook = (Rook) getBoardView().squares[0][this.square.getPozY()].piece;
                if (!rook.isWasMotion()) {
                    for (int i = this.square.getPozX() - 1; i > 0; i--) {
                        //go left
                        if (getBoardView().squares[i][this.square.getPozY()].piece != null) {
                            canCastling = false;
                            break;
                        }
                    }
                    sq = this.getBoardView().squares[this.square.getPozX() - 2][this.square.getPozY()];
                    sq1 = this.getBoardView().squares[this.square.getPozX() - 1][this.square.getPozY()];
                    if (canCastling && this.isSafe(sq) && this.isSafe(sq1)) {
                        //can do castling when none of Sq,sq1 is checked
                        list.add(sq);
                    }
                }
            }
            if (getBoardView().squares[7][this.square.getPozY()].piece != null
                    && (getBoardView().squares[7][this.square.getPozY()].piece.getName().equals("Rook") || getBoardView().squares[7][this.square.getPozY()].piece.getName().equals("SpecialRook"))) {
                boolean canCastling = true;
                Rook rook = (Rook) getBoardView().squares[7][this.square.getPozY()].piece;
                if (!rook.isWasMotion()) {
                    //if king was not moves before and is not checked
                    for (int i = this.square.getPozX() + 1; i < 7; i++) {
                        //go right
                        if (getBoardView().squares[i][this.square.getPozY()].piece != null) {
                            //if square is not empty
                            canCastling = false;//cannot castling
                            break; // exit
                        }
                    }
                    sq = this.getBoardView().squares[this.square.getPozX() + 2][this.square.getPozY()];
                    sq1 = this.getBoardView().squares[this.square.getPozX() + 1][this.square.getPozY()];
                    if (canCastling && this.isSafe(sq) && this.isSafe(sq1)) {
                        //can do castling when none of Sq,sq1 is checked
                        list.add(sq);
                    }
                }
            }
        }

        return list;
    }
}
