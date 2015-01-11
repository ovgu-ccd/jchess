package jchess.gui;

import jchess.IOSystem;
import jchess.Logging;
import jchess.Player;
import jchess.mvc.Controller;
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
        if (selectEvent.getGame() == player.getGame()
                && this.player.isActive()
                && !selectEvent.isVisitedIOSystem()) {
            Logging.GUI.debug("Relay SelectEvent");
            (new SelectEvent(selectEvent, true)).emit();
        }
    }

    @Override
    @Handler
    public void handleUpdateBoardEvent(UpdateBoardEvent updateBoardEvent) {
        if (this.player.getGame() == updateBoardEvent.getBoard().getGame()
            && this.player.isActive() && !updateBoardEvent.hasVisitedIOSystem()) {
            Logging.GUI.debug("Relay UpdateBoardEvent");
            (new UpdateBoardEvent(updateBoardEvent, true)).emit();
        }
    }
}
