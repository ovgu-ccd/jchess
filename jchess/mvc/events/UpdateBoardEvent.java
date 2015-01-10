package jchess.mvc.events;


import jchess.Board;

/**
 * Created by andreas on 10.01.15.
 */
public class UpdateBoardEvent extends Event {

    private boolean visitedIOSystem = false;


    private final Board board;


    public UpdateBoardEvent(Board board) {
        super();


        this.board = board;

    }

    public Board getBoard() {
        return board;
    }

    public boolean hasVisitedIOSystem() {
        return visitedIOSystem;
    }

    public void setVisitedIOSystem(boolean visitedIOSystem) {
        this.visitedIOSystem = visitedIOSystem;
    }

}
