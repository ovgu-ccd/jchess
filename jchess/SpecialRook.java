package jchess;

import jchess.gui.Chessboard;

import java.util.ArrayList;

/**
 * Created by Severin Orth on 20.10.14.
 * This file is part of the de.ovgu.cse.vecs project
 */
public class SpecialRook extends Rook {

    public SpecialRook(Chessboard chessboard, Player player) {
        super(chessboard, player);
    }


    @Override
    public ArrayList allMoves() {
        ArrayList<Square> moves = new ArrayList<>();

        for (Object square : super.allMoves()) {

            if (((Square) square).getPozX() == this.square.getPozX() && Math.abs(((Square) square).getPozY() - this.square.getPozY())
                    <= 5) {
                moves.add((Square) square);
            }

            if (((Square) square).getPozY() == this.square.getPozY() && Math.abs(((Square) square).getPozX() - this.square.getPozX())
                    <= 5) {
                moves.add((Square) square);
            }

        }

        return moves;
    }

}
