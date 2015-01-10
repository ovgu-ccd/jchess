package jchess.gui;

import jchess.*;
import jchess.mvc.Controller;
import jchess.mvc.events.SelectEvent;
import jchess.mvc.events.UpdateBoardEvent;
import net.engio.mbassy.listener.Handler;

/**
 * Created by andreas on 06.12.14.
 */
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
        Logging.GUI.debug("Relay Select Event");
        if (this.player.isActive() && !selectEvent.hasVisitedIOSystem()) {

            selectEvent.setVisitedIOSystem(true);
            selectEvent.emit();
        }
    }

    @Override
    @Handler public void handleUpdateBoardEvent(UpdateBoardEvent updateBoardEvent) {
        if (this.player.isActive() && !updateBoardEvent.hasVisitedIOSystem()) {
            updateBoardEvent.setVisitedIOSystem(true);
            updateBoardEvent.emit();
        }
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }
}
