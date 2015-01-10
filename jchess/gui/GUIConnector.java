package jchess.gui;

import jchess.*;
import jchess.mvc.Controller;
import jchess.mvc.events.SelectEvent;
import jchess.mvc.events.UpdateBoardEvent;

/**
 * Created by andreas on 06.12.14.
 */
public class GUIConnector implements IOSystem {

    private GameTab gameTab;
    private Player player;

    public GUIConnector(GameTab gameTab) {
        this.gameTab = gameTab;
        Controller.INSTANCE.subscribe(this);
    }

    @Override public void handleUpdate(Board board) {

    }

    @Override public void handleNextPlayer() {

    }

    @Override
    public void handlePostMessage(ChatMessage chatMessage) {

    }

    @Override
    public void handleSelectEvent(SelectEvent selectEvent) {
        if (this.player.isActive() && !selectEvent.hasVisitedIOSystem()) {
            selectEvent.setVisitedIOSystem(true);
            selectEvent.emit();
        }
    }

    @Override
    public void handleUpdateBoardEvent(UpdateBoardEvent updateBoardEvent) {
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
