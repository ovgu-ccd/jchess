package jchess.gui;

import jchess.eventbus.Controller;
import jchess.eventbus.events.*;
import jchess.game.IOSystem;
import jchess.game.Player;
import jchess.util.Logging;
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

    @Handler
    void handleUpdateStatusMessageEvent(UpdateStatusMessageEvent updateStatusMessageEvent) {
        if (updateStatusMessageEvent.shouldRelay(this.player.getGame()) && this.player.isActive()) {
            Logging.GUI.debug("GUIConnector: Relay UpdateBoardEvent");
            (new UpdateStatusMessageEvent(updateStatusMessageEvent)).emit();
        }
    }


    @Override
    @Handler
    public void handlePossibleMovesEvent(PossibleMovesEvent possibleMovesEvent) {
        if (possibleMovesEvent.shouldRelay(this.player.getGame()) && this.player.isActive()) {
            Logging.GUI.debug("GUIConnector: Relay PossibleMovesEvent");
            (new PossibleMovesEvent(possibleMovesEvent)).emit();
        }
    }


    @Override
    @Handler
    public void handlePossiblePromotionsEvent(PossiblePromotionsEvent possiblePromotionsEvent) {
        if (possiblePromotionsEvent.shouldRelay(this.player.getGame()) && this.player.isActive()) {
            Logging.GUI.debug("GUIConnector: Relay PossiblePromotionsEvent");
            (new PossiblePromotionsEvent(possiblePromotionsEvent)).emit();
        }
    }

    @Override
    @Handler
    public void handlePromotionSelectEvent(PromotionSelectEvent promotionSelectEvent) {
        if (promotionSelectEvent.shouldRelay(this.player.getGame()) && this.player.isActive()) {
            Logging.GUI.debug("GUIConnector: Relay PromotionSelectEvent");
            (new PromotionSelectEvent(promotionSelectEvent)).emit();
        }
    }
}
