package jchess.mvc.events;

import jchess.Game;
import jchess.pieces.Piece;

/**
 * Created by andreas on 12.01.15.
 *
 * @trace [$REQ29]
 * @trace [$REQ07]
 */
public class PromotionSelectEvent extends AbstractIOSystemRelayEvent {
    private final Class<? extends Piece> pieceClass;


    public PromotionSelectEvent(Game game, Class<? extends Piece> pieceClass) {
        super(game);
        this.pieceClass = pieceClass;
    }


    public PromotionSelectEvent(PromotionSelectEvent promotionSelectEvent) {
        super(promotionSelectEvent);
        this.pieceClass = promotionSelectEvent.getPieceClass();
    }


    public Class<? extends Piece> getPieceClass() {
        return pieceClass;
    }
}
