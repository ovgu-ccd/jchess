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

    public boolean shouldRelay(Game game) {
        return this.game == game && !hasVisitedIOSystem();
    }

    public boolean shouldReceive(Game game) {
        return this.game == game && hasVisitedIOSystem();
    }

    public Game getGame() {
        return game;
    }

    public Board getBoard() {
        return game.getBoard();
    }
}
