package jchess.eventbus.events;


import jchess.game.Game;

/**
 * Created by andreas on 10.01.15.
 */
public class UpdateBoardEvent extends AbstractIOSystemRelayEvent {

    public UpdateBoardEvent(Game game) {
        super(game);
    }

    public UpdateBoardEvent(UpdateBoardEvent updateBoardEvent) {
        super(updateBoardEvent);
    }
}
