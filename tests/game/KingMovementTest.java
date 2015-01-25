package tests.game;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import jchess.eventbus.events.SelectEvent;
import jchess.game.Game;
import jchess.game.IOSystem;
import jchess.game.board.Board;
import jchess.game.board.InvalidBoardCoordinateException;
import jchess.game.pieces.King;
import jchess.game.pieces.Pawn;
import jchess.game.pieces.Rook;
import jchess.util.BoardCoordinate;
import jchess.util.Logging;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by robert on 24/01/15.
 */

public class KingMovementTest {

    @Singleton
    static class KingMovementBoard extends Board {
        @Override
        protected void initFigures() throws InvalidBoardCoordinateException {
            getTile(7, 7).placePiece(new King(0));
        }
    }

    private final Injector injector;

    private Game game;

    public KingMovementTest() {
        Logging.setup();
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Board.class).to(KingMovementBoard.class);
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
    public void possibleMovesKing() {
        SelectEvent selectEvent = new SelectEvent(game, new BoardCoordinate(7, 7));
        emitAndSimulateRelay(selectEvent);

        assertEquals(12, game.getPossibleMovesCoordinates().size());

        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(6, 6)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(6, 7)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(7, 8)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(8, 8)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(8, 7)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(7, 6)));

        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(5, 6)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(6, 8)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(8, 9)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(9, 8)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(8, 6)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(6, 5)));

    }


    void emitAndSimulateRelay(SelectEvent selectEvent) {
        // Simulate relay
        selectEvent = new SelectEvent(selectEvent);
        game.handleSelectEvent(selectEvent);
    }
}
