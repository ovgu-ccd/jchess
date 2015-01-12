package jchess.mvc.events;

import jchess.Game;
import jchess.pieces.Pieces;

/**
 * Created by andreas on 12.01.15.
 */
public class PromotionSelectEvent extends AbstractIOSystemRelayEvent {
    private final Pieces pieces;

    public PromotionSelectEvent(Game game, Pieces pieces) {
        super(game);
        this.pieces = pieces;
    }

    public PromotionSelectEvent(PromotionSelectEvent promotionSelectEvent) {
        super(promotionSelectEvent);
        this.pieces = promotionSelectEvent.getPieces();
    }

    public Pieces getPieces() {
        return pieces;
    }
}
