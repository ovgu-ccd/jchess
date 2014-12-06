package jchess;

import jchess.gui.BoardView;

import java.util.ArrayList;

public class HyperPawn extends Pawn {

    public HyperPawn(BoardView boardView, Player player) {
        super(boardView, player);
    }

    @Override
    public ArrayList allMoves() {
        // System.out.println(this.player.goDown);//4test
        ArrayList list = new ArrayList();
        Square sq;
        Square sq1;
        int first = this.square.getPozY() - 1;// number where to move
        int second = this.square.getPozY() - 2;// number where to move (only in first
        // move)
        if (this.player.isGoDown()) {// check if player "go" down or up
            first = this.square.getPozY() + 1;// if yes, change value
            second = this.square.getPozY() + 2;// if yes, change value
        }
        if (this.isout(first, first)) {// out of bounds protection
            return list;// return empty list
        }
        sq = getBoardView().squares[this.square.getPozX()][first];
        if (sq.piece == null) {// if next is free
            // list.add(sq);//add
            if (this.player.getColor() == Player.colors.white) {// white

                if (this.getBoardView().kingWhite.willBeSafeWhenMoveOtherPiece(
                            this.square,
                            getBoardView().squares[this.square.getPozX()][first])) {
                    list.add(getBoardView().squares[this.square.getPozX()][first]);
                }
            } else {// or black

                if (this.getBoardView().kingBlack.willBeSafeWhenMoveOtherPiece(
                            this.square,
                            getBoardView().squares[this.square.getPozX()][first])) {
                    list.add(getBoardView().squares[this.square.getPozX()][first]);
                }
            }

            if ((player.isGoDown() /*&& this.square.pozY == 1*/)
                    || (!player.isGoDown() /*&& this.square.pozY == 6*/)) {
                sq1 = getBoardView().squares[this.square.getPozX()][second];
                if (sq1.piece == null) {
                    // list.add(sq1);//only in first move
                    if (this.player.getColor() == Player.colors.white) {// white

                        if (this.getBoardView().kingWhite
                                .willBeSafeWhenMoveOtherPiece(
                                    this.square,
                                    getBoardView().squares[this.square.getPozX()][second])) {
                            list.add(getBoardView().squares[this.square.getPozX()][second]);
                        }
                    } else {// or black

                        if (this.getBoardView().kingBlack
                                .willBeSafeWhenMoveOtherPiece(
                                    this.square,
                                    getBoardView().squares[this.square.getPozX()][second])) {
                            list.add(getBoardView().squares[this.square.getPozX()][second]);
                        }
                    }
                }
            }
        }
        if (!this.isout(this.square.getPozX() - 1, this.square.getPozY())) // out of
            // bounds
            // protection
        {
            // capture
            sq = getBoardView().squares[this.square.getPozX() - 1][first];
            if (sq.piece != null) {// check if can hit left
                if (this.player != sq.piece.player
                        && !sq.piece.getName().equals("King")) {
                    // list.add(sq);
                    if (this.player.getColor() == Player.colors.white) {// white

                        if (this.getBoardView().kingWhite
                                .willBeSafeWhenMoveOtherPiece(
                                    this.square,
                                    getBoardView().squares[this.square.getPozX() - 1][first])) {
                            list.add(getBoardView().squares[this.square.getPozX() - 1][first]);
                        }
                    } else {// or black

                        if (this.getBoardView().kingBlack
                                .willBeSafeWhenMoveOtherPiece(
                                    this.square,
                                    getBoardView().squares[this.square.getPozX() - 1][first])) {
                            list.add(getBoardView().squares[this.square.getPozX() - 1][first]);
                        }
                    }
                }
            }

            // En passant
            sq = getBoardView().squares[this.square.getPozX() - 1][this.square.getPozY()];
            if (sq.piece != null && this.getBoardView().twoSquareMovedPawn != null
                    && sq == this.getBoardView().twoSquareMovedPawn.square) {// check
                // if
                // can
                // hit
                // left
                if (this.player != sq.piece.player
                        && !sq.piece.getName().equals("King")) {// unnecessary

                    // list.add(sq);
                    if (this.player.getColor() == Player.colors.white) {// white

                        if (this.getBoardView().kingWhite
                                .willBeSafeWhenMoveOtherPiece(
                                    this.square,
                                    getBoardView().squares[this.square.getPozX() - 1][first])) {
                            list.add(getBoardView().squares[this.square.getPozX() - 1][first]);
                        }
                    } else {// or black

                        if (this.getBoardView().kingBlack
                                .willBeSafeWhenMoveOtherPiece(
                                    this.square,
                                    getBoardView().squares[this.square.getPozX() - 1][first])) {
                            list.add(getBoardView().squares[this.square.getPozX() - 1][first]);
                        }
                    }
                }
            }
        }
        if (!this.isout(this.square.getPozX() + 1, this.square.getPozY())) {// out of
            // bounds
            // protection

            // capture
            sq = getBoardView().squares[this.square.getPozX() + 1][first];
            if (sq.piece != null) {// check if can hit right
                if (this.player != sq.piece.player
                        && !sq.piece.getName().equals("King")) {
                    // list.add(sq);
                    if (this.player.getColor() == Player.colors.white) { // white

                        if (this.getBoardView().kingWhite
                                .willBeSafeWhenMoveOtherPiece(
                                    this.square,
                                    getBoardView().squares[this.square.getPozX() + 1][first])) {
                            list.add(getBoardView().squares[this.square.getPozX() + 1][first]);
                        }
                    } else {// or black

                        if (this.getBoardView().kingBlack
                                .willBeSafeWhenMoveOtherPiece(
                                    this.square,
                                    getBoardView().squares[this.square.getPozX() + 1][first])) {
                            list.add(getBoardView().squares[this.square.getPozX() + 1][first]);
                        }
                    }
                }
            }

            // En passant
            sq = getBoardView().squares[this.square.getPozX() + 1][this.square.getPozY()];
            if (sq.piece != null && this.getBoardView().twoSquareMovedPawn != null
                    && sq == this.getBoardView().twoSquareMovedPawn.square) {// check
                // if
                // can
                // hit
                // left
                if (this.player != sq.piece.player
                        && !sq.piece.getName().equals("King")) {// unnecessary

                    // list.add(sq);
                    if (this.player.getColor() == Player.colors.white) {// white

                        if (this.getBoardView().kingWhite
                                .willBeSafeWhenMoveOtherPiece(
                                    this.square,
                                    getBoardView().squares[this.square.getPozX() + 1][first])) {
                            list.add(getBoardView().squares[this.square.getPozX() + 1][first]);
                        }
                    } else {// or black

                        if (this.getBoardView().kingBlack
                                .willBeSafeWhenMoveOtherPiece(
                                    this.square,
                                    getBoardView().squares[this.square.getPozX() + 1][first])) {
                            list.add(getBoardView().squares[this.square.getPozX() + 1][first]);
                        }
                    }
                }
            }
        }

        return list;
    }

}
