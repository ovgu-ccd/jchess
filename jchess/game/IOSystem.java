package jchess.game;

import jchess.eventbus.events.*;
import net.engio.mbassy.listener.Handler;

/**
 * Bridge between GL and User Input.
 *
 * Created by andreas on 06.12.14.
 *
 * @trace [$REQ07]
 */
@SuppressWarnings("UnusedDeclaration")
public interface IOSystem {
    @Handler
    void handleSelectEvent(SelectEvent selectEvent);

    @Handler
    void handleUpdateBoardEvent(UpdateBoardEvent updateBoardEvent);

    @Handler
    void handlePossibleMovesEvent(PossibleMovesEvent possibleMovesEvent);

    @Handler
    void handlePossiblePromotionsEvent(PossiblePromotionsEvent possiblePromotionsEvent);

    @Handler
    void handlePromotionSelectEvent(PromotionSelectEvent promotionSelectEvent);

    @Handler
    void handleUpdateStatusMessageEvent(UpdateStatusMessageEvent updateStatusMessageEvent);

    void setPlayer(Player player);
}
