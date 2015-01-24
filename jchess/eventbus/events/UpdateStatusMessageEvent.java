package jchess.eventbus.events;

import jchess.game.Game;

/**
 * Created by robert on 20.01.15.
 */
public class UpdateStatusMessageEvent extends AbstractIOSystemRelayEvent {
    private String statusMessage;

    ;
    private Types types;
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

    public String getStatusMessage() {
        return statusMessage;
    }

    public Types getTypes() {
        return types;
    }

    public enum Types {NORMAL, ALERT}
}
