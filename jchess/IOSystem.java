package jchess;

/**
 * Created by andreas on 06.12.14.
 */
public interface IOSystem {
    void handleUpdate(Board board);
    void handleNextPlayer();
    void handlePostMessage(ChatMessage chatMessage);
}
