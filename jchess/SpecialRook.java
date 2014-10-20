package jchess;

import java.util.ArrayList;

/**
 * Created by Severin Orth on 20.10.14.
 * This file is part of the de.ovgu.cse.vecs project
 */
public class SpecialRook extends Rook {

    SpecialRook(Chessboard chessboard, Player player) {
        super(chessboard, player);
    }


    @Override
    public ArrayList allMoves() {
        ArrayList<Square> moves = new ArrayList<>();

        for (Object square : super.allMoves()) {

            if (((Square) square).pozX == this.square.pozX &&
                    Math.abs(((Square) square).pozY - this.square.pozY) < 5) {
                moves.add((Square) square);
            }

            if (((Square) square).pozY == this.square.pozY &&
                    Math.abs(((Square) square).pozX - this.square.pozX) < 5) {
                moves.add((Square) square);
            }

        }

        return moves;
    }

}
