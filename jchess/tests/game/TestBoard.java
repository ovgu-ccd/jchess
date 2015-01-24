package jchess.tests.game;

import jchess.game.board.Board;
import jchess.game.pieces.Pawn;

/**
 * Created by robert on 24/01/15.
 */
public class TestBoard extends Board {
    @Override
    protected void initFigures() {
        getTile(0,0).placePiece(new Pawn(0));
    }
}
