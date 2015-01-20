package jchess.mvc.events;

import jchess.Game;
import jchess.pieces.Piece;

import java.util.Set;

/**
 * Created by andreas on 12.01.15.
 *
 * @trace [$REQ07]
 * @trace [$REQ29]
 */
public class PossiblePromotionsEvent extends AbstractIOSystemRelayEvent {
    private final Set<Class<? extends Piece>> possiblePromotions;


    public PossiblePromotionsEvent(Game game, Set<Class<? extends Piece>> possiblePromotions) {
        super(game);
        this.possiblePromotions = possiblePromotions;
    }


    public PossiblePromotionsEvent(PossiblePromotionsEvent possiblePromotionsEvent) {
        super(possiblePromotionsEvent);
        this.possiblePromotions = possiblePromotionsEvent.getPossiblePromotions();
    }


    public Set<Class<? extends Piece>> getPossiblePromotions() {
        return possiblePromotions;
    }
}
