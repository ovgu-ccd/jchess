package jchess.mvc.events;

import jchess.Game;
import jchess.pieces.PieceNames;

import java.util.Set;

/**
 * Created by andreas on 12.01.15.
 */
public class PossiblePromotionsEvent extends AbstractIOSystemRelayEvent {
    private final Set<PieceNames> possiblePromotions;

    public PossiblePromotionsEvent(Game game, Set<PieceNames> possiblePromotions) {
        super(game);
        this.possiblePromotions = possiblePromotions;
    }

    public PossiblePromotionsEvent(PossiblePromotionsEvent possiblePromotionsEvent) {
        super(possiblePromotionsEvent);
        this.possiblePromotions = possiblePromotionsEvent.getPossiblePromotions();
    }

    public Set<PieceNames> getPossiblePromotions() {
        return possiblePromotions;
    }
}
