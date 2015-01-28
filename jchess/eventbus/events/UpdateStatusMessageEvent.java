package jchess.eventbus.events;

import jchess.game.Game;

/**
 * Show a notification
 * <p/>
 * Created by robert on 20.01.15.
 *
 * @trace [$REQ07]
 * @flow GL -> GUI
 * @flow GUI -> GUI
 */
public class UpdateStatusMessageEvent extends AbstractIOSystemRelayEvent {
    public enum Types {
        NORMAL,
        ALERT
    }
    private final String statusMessage;
    private final Types types;

    public UpdateStatusMessageEvent(Game game, String statusMessage, Types type) {
        super(game);
        this.statusMessage = statusMessage;
        this.types = type;
    }

    public UpdateStatusMessageEvent(UpdateStatusMessageEvent abstractIOSystemRelayEvent) {
        super(abstractIOSystemRelayEvent);
        this.statusMessage = abstractIOSystemRelayEvent.statusMessage;
        this.types = abstractIOSystemRelayEvent.types;
    }

    /**
     * Getter for the status message assigned with this event.
     * @return String
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Getter for the type of the status message
     * @return Enum Types
     */
    public Types getTypes() {
        return types;
    }
}
