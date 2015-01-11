package jchess.mvc.events;

import jchess.Game;
import jchess.util.BoardCoordinate;

/**
 * Created by andreas on 10.01.15.
 */
public class SelectEvent extends AbstractIOSystemRelayEvent {
    private final BoardCoordinate boardCoordinate;

    public SelectEvent(Game game, BoardCoordinate boardCoordinate) {
        super(game);
        this.boardCoordinate = boardCoordinate;
    }

    public SelectEvent(SelectEvent selectEvent, boolean visitedIOSystem) {
        super(selectEvent, visitedIOSystem);
        this.boardCoordinate = selectEvent.getBoardCoordinate();
    }

    public BoardCoordinate getBoardCoordinate() {
        return boardCoordinate;
    }
}
