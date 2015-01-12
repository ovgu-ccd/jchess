package jchess.mvc.events;

import jchess.Game;
import jchess.pieces.Pieces;

import java.util.Set;

/**
 * Created by andreas on 12.01.15.
 */
public class PossiblePromotionsEvent extends AbstractIOSystemRelayEvent {
    private final Set<Pieces> possiblePromotions;

    public PossiblePromotionsEvent(Game game, Set<Pieces> possiblePromotions) {
        super(game);
        this.possiblePromotions = possiblePromotions;
    }

    public PossiblePromotionsEvent(PossiblePromotionsEvent possiblePromotionsEvent) {
        super(possiblePromotionsEvent);
        this.possiblePromotions = possiblePromotionsEvent.getPossiblePromotions();
    }

    public Set<Pieces> getPossiblePromotions() {
        return possiblePromotions;
    }
}
