package jchess;

import jchess.mvc.events.PossibleMovesEvent;
import jchess.mvc.events.SelectEvent;
import jchess.mvc.events.UpdateBoardEvent;
import net.engio.mbassy.listener.Handler;

/**
 * Created by andreas on 06.12.14.
 */
public interface IOSystem {
    @Handler void handleSelectEvent(SelectEvent selectEvent);
    @Handler void handleUpdateBoardEvent(UpdateBoardEvent updateBoardEvent);
    @Handler void handlePossibleMovesEvent(PossibleMovesEvent possibleMovesEvent);
    public void setPlayer(Player player);
}
