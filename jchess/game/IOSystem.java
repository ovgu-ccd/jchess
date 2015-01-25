package jchess.game;

import jchess.eventbus.events.*;
import net.engio.mbassy.listener.Handler;

/**
 * Bridge between GL and GUI
 *
 * @trace [$REQ07]
 * <p/>
 * Created by andreas on 06.12.14.
 */
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
