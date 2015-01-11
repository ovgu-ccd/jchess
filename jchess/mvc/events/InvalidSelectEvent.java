package jchess.mvc.events;

import jchess.Game;

/**
 * Created by andreas on 11.01.15.
 */
public class InvalidSelectEvent extends AbstractIOSystemRelayEvent {
    public InvalidSelectEvent(Game game) {
        super(game);
    }

    public InvalidSelectEvent(AbstractIOSystemRelayEvent abstractIOSystemRelayEvent) {
        super(abstractIOSystemRelayEvent);
    }
}
