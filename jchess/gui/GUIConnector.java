package jchess.gui;

import jchess.*;
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

    private GameTab gameTab;
    private Player player;

    public GUIConnector(GameTab gameTab) {
        Controller.INSTANCE.subscribe(this);
        this.gameTab = gameTab;
    }

    @Override public void handleUpdate(Board board) {

    }

    @Override public void handleNextPlayer() {

    }

    @Override
    public void handlePostMessage(ChatMessage chatMessage) {

    }

    @Override
    @Handler public void handleSelectEvent(SelectEvent selectEvent) {
        if (this.player.isActive() && !selectEvent.isVisitedIOSystem()) {
            Logging.GUI.debug("Relay SelectEvent");
            (new SelectEvent(selectEvent, true)).emit();
        }
    }

    @Override
    @Handler public void handleUpdateBoardEvent(UpdateBoardEvent updateBoardEvent) {
        if (this.player.isActive() && !updateBoardEvent.hasVisitedIOSystem()) {
            updateBoardEvent.setVisitedIOSystem(true);
            Logging.GUI.debug("Relay UpdateBoardEvent");
            (new UpdateBoardEvent(updateBoardEvent, true)).emit();
        }
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }
}
