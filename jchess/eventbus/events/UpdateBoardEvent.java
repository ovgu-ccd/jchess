package jchess.eventbus.events;


import jchess.game.Game;

/**
 * Redraw the game event
 * <p/>
 * Created by andreas on 10.01.15.
 *
 * @trace [$REQ07]
 * @flow GL -> GUI
 */
public class UpdateBoardEvent extends AbstractIOSystemRelayEvent {

    public UpdateBoardEvent(Game game) {
        super(game);
    }

    public UpdateBoardEvent(UpdateBoardEvent updateBoardEvent) {
        super(updateBoardEvent);
    }
}
