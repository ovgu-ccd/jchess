package jchess.tests.game;

import jchess.eventbus.events.*;
import jchess.game.IOSystem;
import jchess.game.Player;

/**
 * Created by robert on 24/01/15.
 */
public class IOSystemMock implements IOSystem {
    @Override
    public void handleSelectEvent(SelectEvent selectEvent) {

    }

    @Override
    public void handleUpdateBoardEvent(UpdateBoardEvent updateBoardEvent) {

    }

    @Override
    public void handlePossibleMovesEvent(PossibleMovesEvent possibleMovesEvent) {

    }

    @Override
    public void handlePossiblePromotionsEvent(PossiblePromotionsEvent possiblePromotionsEvent) {

    }

    @Override
    public void handlePromotionSelectEvent(PromotionSelectEvent promotionSelectEvent) {

    }

    @Override
    public void handleUpdateStatusMessageEvent(UpdateStatusMessageEvent updateStatusMessageEvent) {

    }

    @Override
    public void setPlayer(Player player) {

    }
}
