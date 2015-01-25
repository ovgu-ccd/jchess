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

public class RookMovementTest {

    @Singleton
    static class RookMovementBoard extends Board {
        @Override
        protected void initFigures() throws InvalidBoardCoordinateException {
            getTile(0, 0).placePiece(new Rook(0));
            getTile(6, 0).placePiece(new Pawn(0));
            getTile(0, 6).placePiece(new Pawn(0));
            getTile(13, 13).placePiece(new Pawn(1));
        }
    }

    private final Injector injector;

    private Game game;

    public RookMovementTest() {
        Logging.setup();
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Board.class).to(RookMovementBoard.class);
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
    public void possibleMovesRook() {
        SelectEvent selectEvent = new SelectEvent(game, new BoardCoordinate(0, 0));
        emitAndSimulateRelay(selectEvent);

        assertEquals(23, game.getPossibleMovesCoordinates().size());
        for (int i = 1; i < 14; i++) {
            assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(i, i)));
        }
        for (int i = 1; i < 6; i++) {
            assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(i, 0)));
            assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(0, i)));
        }
    }


    void emitAndSimulateRelay(SelectEvent selectEvent) {
        // Simulate relay
        selectEvent = new SelectEvent(selectEvent);
        game.handleSelectEvent(selectEvent);
    }
}
