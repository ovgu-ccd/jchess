package jchess.mvc.events;

import jchess.Game;
import jchess.pieces.PieceNames;

/**
 * Created by andreas on 12.01.15.
 */
public class PromotionSelectEvent extends AbstractIOSystemRelayEvent {
    private final PieceNames pieceNames;

    public PromotionSelectEvent(Game game, PieceNames pieceNames) {
        super(game);
        this.pieceNames = pieceNames;
    }

    public PromotionSelectEvent(PromotionSelectEvent promotionSelectEvent) {
        super(promotionSelectEvent);
        this.pieceNames = promotionSelectEvent.getPieceNames();
    }

    public PieceNames getPieceNames() {
        return pieceNames;
    }
}
