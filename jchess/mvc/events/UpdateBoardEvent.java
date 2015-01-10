package jchess.mvc.events;

import jchess.Game;

/**
 * Created by andreas on 10.01.15.
 */
public class UpdateBoardEvent extends Event {
    private Game game;
    private boolean visitedIOSystem = false;

    public UpdateBoardEvent(Game game) {
        this.game = game;
    }

    public boolean hasVisitedIOSystem() {
        return visitedIOSystem;
    }

    public void setVisitedIOSystem(boolean visitedIOSystem) {
        this.visitedIOSystem = visitedIOSystem;
    }
}
