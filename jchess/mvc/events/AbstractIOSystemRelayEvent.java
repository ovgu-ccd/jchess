package jchess.mvc.events;
import jchess.Board;
import jchess.Game;

/**
 * Created by andreas on 11.01.15.
 */
public abstract class AbstractIOSystemRelayEvent extends Event {
    private boolean visitedIOSystem = false;
    private final Game game;

    public AbstractIOSystemRelayEvent(Game game) {
        this.game = game;
    }

    public AbstractIOSystemRelayEvent(AbstractIOSystemRelayEvent abstractIOSystemRelayEvent) {
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
