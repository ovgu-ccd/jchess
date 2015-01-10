package jchess;

import jchess.mvc.events.SelectEvent;
import jchess.mvc.events.UpdateBoardEvent;
import net.engio.mbassy.listener.Handler;

/**
 * Created by andreas on 06.12.14.
 */
public interface IOSystem {
    void handleUpdate(Board board);
    void handleNextPlayer();
    void handlePostMessage(ChatMessage chatMessage);
    @Handler void handleSelectEvent(SelectEvent selectEvent);
    @Handler void handleUpdateBoardEvent(UpdateBoardEvent updateBoardEvent);
    public void setPlayer(Player player);
}
