package jchess.mvc.events;

import jchess.Game;
import jchess.util.BoardCoordinate;

/**
 * Created by andreas on 10.01.15.
 */
public class SelectEvent extends Event {
    private BoardCoordinate boardCoordinate;
    private Game game;
    private boolean visitedIOSystem = false;

    public SelectEvent(BoardCoordinate boardCoordinate, Game game) {
        this.boardCoordinate = boardCoordinate;
        this.game = game;
    }

    public BoardCoordinate getBoardCoordinate() {
        return boardCoordinate;
    }

    public void setBoardCoordinate(BoardCoordinate boardCoordinate) {
        this.boardCoordinate = boardCoordinate;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isVisitedIOSystem() {
        return visitedIOSystem;
    }

    public void setVisitedIOSystem(boolean visitedIOSystem) {
        this.visitedIOSystem = visitedIOSystem;
    }
}
