package jchess.tests.game;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import jchess.eventbus.events.SelectEvent;
import jchess.game.Game;
import jchess.game.IOSystem;
import jchess.game.board.Board;
import jchess.util.BoardCoordinate;
import jchess.util.Logging;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by robert on 24/01/15.
 */
public class PawnMovementTest {
    private Injector injector;
    private Game game;

    public PawnMovementTest() {
        Logging.setup();
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Board.class).to(TestBoard.class);
            }
        });
    }

    @Before
    public void setup() {
        game = injector.getInstance(Game.class);
        game.initializeGame(new String[]{"Player1", "Player2", "Player3"},
                new IOSystem[]{injector.getInstance(IOSystemMock.class),
                        injector.getInstance(IOSystemMock.class),
                        injector.getInstance(IOSystemMock.class)});
    }

    @Test
    public void possibleMoves() {
        SelectEvent selectEvent = new SelectEvent(game, new BoardCoordinate(0, 0));

        // Simulate relay
        selectEvent = new SelectEvent(selectEvent);
        selectEvent.emit();

        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(1, 0)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(2, 0)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(1, 1)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(2, 2)));
    }
}
