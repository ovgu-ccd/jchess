package jchess.mvc.events;

import jchess.Board;


/**
 * Created by Severin Orth on 07.12.14.
 *
 * NewGame message for creating a new game
 */
public class UpdateBoardEvent extends Event {

    private final Board board;


    public UpdateBoardEvent(Board board) {
        super();

        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

}
