package jchess;

import jchess.mvc.events.SelectEvent;
import net.engio.mbassy.listener.Handler;

/**
 * Created by andreas on 06.12.14.
 */
public interface IOSystem {
    void handleUpdate(Board board);
    void handleNextPlayer();
    void handlePostMessage(ChatMessage chatMessage);
    @Handler void handleSelectEvent(SelectEvent selectEvent);
    public void setPlayer(Player player);
}
