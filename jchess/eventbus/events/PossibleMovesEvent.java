package jchess.eventbus.events;

import jchess.game.Game;
import jchess.util.BoardCoordinate;

import java.util.Set;

/**
 * Event for sending the possible moves from GL top GUI
 * <p/>
 * Created by andreas on 11.01.15.
 *
 * @trace [$REQ07]
 * @flow GL -> GUI
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

    /**
     * Getter for BoardCoordinates assigned with this event.
     * @return Set of BoardCoordinates
     */
    public Set<BoardCoordinate> getBoardCoordinates() {
        return boardCoordinates;
    }
}
