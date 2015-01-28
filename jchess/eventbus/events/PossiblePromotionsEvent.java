package jchess.eventbus.events;

import jchess.game.Game;
import jchess.game.pieces.Piece;

import java.util.Set;

/**
 * Created by andreas on 12.01.15.
 *
 * @trace [$REQ07]
 * @trace [$REQ29]
 * @flow GL -> GUI
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

    /**
     * Getter for the possible promotions assigned with this event.
     * @return Set of piece classes
     */
    public Set<Class<? extends Piece>> getPossiblePromotions() {
        return possiblePromotions;
    }
}
