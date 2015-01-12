package jchess.mvc.events;

import jchess.Game;
import jchess.pieces.Piece;

/**
 * Created by andreas on 12.01.15.
 */
public class PromotionSelectEvent extends AbstractIOSystemRelayEvent {
    private final Class<? extends Piece> piece;

    public PromotionSelectEvent(Game game, Class<? extends Piece> piece) {
        super(game);
        this.piece = piece;
    }

    public PromotionSelectEvent(PromotionSelectEvent promotionSelectEvent) {
        super(promotionSelectEvent);
        this.piece = promotionSelectEvent.getPiece();
    }

    public Class<? extends Piece> getPiece() {
        return piece;
    }
}
