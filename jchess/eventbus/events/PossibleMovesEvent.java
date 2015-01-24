package jchess.eventbus.events;

import jchess.game.Game;
import jchess.util.BoardCoordinate;

import java.util.Set;

/**
 * Created by andreas on 11.01.15.
 */
public class PossibleMovesEvent extends AbstractIOSystemRelayEvent {
    private final Set<BoardCoordinate> boardCoordinates;

    public PossibleMovesEvent(Game game, Set<BoardCoordinate> boardCoordinates) {
        super(game);
        this.boardCoordinates = boardCoordinates;
    }

    public PossibleMovesEvent(PossibleMovesEvent possibleMovesEvent) {
        super(possibleMovesEvent);
        this.boardCoordinates = possibleMovesEvent.getBoardCoordinates();
    }

    public Set<BoardCoordinate> getBoardCoordinates() {
        return boardCoordinates;
    }
}
