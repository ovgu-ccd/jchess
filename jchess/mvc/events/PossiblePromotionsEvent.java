package jchess.mvc.events;

import jchess.Game;
import jchess.pieces.Piece;

import java.util.Collection;
import java.util.Set;

/**
 * Created by robert on 12/01/15.
 */
public class PossiblePromotionsEvent extends AbstractIOSystemRelayEvent{
    private Set<Class<? extends Piece>> possiblePromotions;

    public PossiblePromotionsEvent(Game game) {
        super(game);
    }

    public PossiblePromotionsEvent(PossiblePromotionsEvent possiblePromotionsEvent) {
        super(possiblePromotionsEvent);
    }


    public Set<Class<? extends Piece>> getPossiblePromotions() {
        return possiblePromotions;
    }
}
