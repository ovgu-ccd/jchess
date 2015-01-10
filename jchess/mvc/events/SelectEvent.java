package jchess.mvc.events;

import jchess.Game;
import jchess.util.BoardCoordinate;

/**
 * Created by andreas on 10.01.15.
 */
public class SelectEvent extends Event {
    private BoardCoordinate bc;
    private Game game;
    private boolean visitedIOSystem = false;

    public SelectEvent(BoardCoordinate boardCoordinate, Game game) {
        this.bc = boardCoordinate;
        this.game = game;
    }

    public boolean hasVisitedIOSystem() {
        return visitedIOSystem;
    }

    public void setVisitedIOSystem(boolean visitedIOSystem) {
        this.visitedIOSystem = visitedIOSystem;
    }
}
