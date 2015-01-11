package jchess.gui;

import jchess.IOSystem;
import jchess.Logging;
import jchess.Player;
import jchess.mvc.Controller;
import jchess.mvc.events.InvalidSelectEvent;
import jchess.mvc.events.PossibleMovesEvent;
import jchess.mvc.events.SelectEvent;
import jchess.mvc.events.UpdateBoardEvent;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;

/**
 * Created by andreas on 06.12.14.
 */
@Listener(references = References.Strong)
public class GUIConnector implements IOSystem {

    private Player player;

    public GUIConnector() {
        Controller.INSTANCE.subscribe(this);
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    @Handler
    public void handleSelectEvent(SelectEvent selectEvent) {
        if (selectEvent.shouldRelay(this.player.getGame()) && this.player.isActive()) {
            Logging.GUI.debug("GUIConnector: Relay SelectEvent");
            (new SelectEvent(selectEvent)).emit();
        }
    }

    @Override
    @Handler
    public void handleUpdateBoardEvent(UpdateBoardEvent updateBoardEvent) {
        if (updateBoardEvent.shouldRelay(this.player.getGame()) && this.player.isActive()) {
            Logging.GUI.debug("GUIConnector: Relay UpdateBoardEvent");
            (new UpdateBoardEvent(updateBoardEvent)).emit();
        }
    }

    @Override
    @Handler
    public void handlePossibleMovesEvent(PossibleMovesEvent possibleMovesEvent) {
        // TODO
        if (possibleMovesEvent.shouldRelay(this.player.getGame()) && this.player.isActive()) {
            Logging.GUI.debug("GUIConnector: Relay PossibleMovesEvent");
            (new PossibleMovesEvent(possibleMovesEvent)).emit();
        }
    }

    @Override
    @Handler
    public void handleInvalidSelectEvent(InvalidSelectEvent invalidSelectEvent) {
        // TODO
        if (invalidSelectEvent.shouldRelay(this.player.getGame()) && this.player.isActive()) {
            Logging.GUI.debug("GUIConnector: Relay InvalidSelectEvent");
            (new InvalidSelectEvent(invalidSelectEvent)).emit();
        }
    }
}
