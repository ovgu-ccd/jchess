package jchess.mvc.events;

import jchess.util.BoardCoordinate;
import java.util.Collections;
import java.util.Set;

/**
 * Created by andreas on 11.01.15.
 */
public class PossibleMovesEvent extends Event {
    private final Set<BoardCoordinate> boardCoordinates;

    public PossibleMovesEvent(Set<BoardCoordinate> boardCoordinates) {
        this.boardCoordinates = Collections.unmodifiableSet(boardCoordinates);
    }
}
