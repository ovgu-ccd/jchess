package jchess.eventbus.events;

import jchess.game.Game;
import jchess.game.pieces.Piece;

/**
 * Created by andreas on 12.01.15.
 *
 * @trace [$REQ29]
 * @trace [$REQ07]
 * @flow GUI -> GL
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
