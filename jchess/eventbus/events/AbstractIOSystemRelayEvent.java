package jchess.eventbus.events;

import jchess.game.Game;
import jchess.game.board.Board;

/**
 * Base relay event that is handled by IOSystem
 * <p/>
 * Created by andreas on 11.01.15.
 *
 * @trace [$REQ07]
 */
public abstract class AbstractIOSystemRelayEvent extends Event {
    private final Game game;
    private boolean visitedIOSystem = false;

    AbstractIOSystemRelayEvent(Game game) {
        this.game = game;
    }

    AbstractIOSystemRelayEvent(AbstractIOSystemRelayEvent abstractIOSystemRelayEvent) {
        this.game = abstractIOSystemRelayEvent.getGame();
        this.visitedIOSystem = true;
    }

    private boolean hasVisitedIOSystem() {
        return visitedIOSystem;
    }

    /**
     * This method tests an event if it should be relayed by the receiving object.
     * @param game The game for which the event is specified
     * @return Boolean value that defines whether the event should be relayed or not
     */
    public boolean shouldRelay(Game game) {
        return this.game == game && !hasVisitedIOSystem();
    }

    /**
     * This method tests an event if it should be received by the receiving object.
     * @param game The game for which the event is specified
     * @return Boolean value that defines whether the event should be received or not
     */
    public boolean shouldReceive(Game game) {
        return this.game == game && hasVisitedIOSystem();
    }

    /**
     * Getter for game.
     * @return Game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Getter for the board assigned with the game
     * @return Board
     */
    public Board getBoard() {
        return game.getBoard();
    }
}
