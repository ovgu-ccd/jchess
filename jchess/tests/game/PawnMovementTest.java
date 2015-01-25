package jchess.tests.game;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import jchess.eventbus.events.SelectEvent;
import jchess.game.Game;
import jchess.game.IOSystem;
import jchess.game.board.Board;
import jchess.game.board.InvalidBoardCoordinateException;
import jchess.game.pieces.Pawn;
import jchess.util.BoardCoordinate;
import jchess.util.Logging;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by robert on 24/01/15.
 */
public class PawnMovementTest extends Board {
    private Injector injector;
    private Game game;

    public PawnMovementTest() throws InvalidBoardCoordinateException {
        Logging.setup();
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Board.class).to(PawnMovementTest.class);
            }
        });
    }

    @Override
    protected void initFigures() throws InvalidBoardCoordinateException {
        getTile(6, 0).placePiece(new Pawn(0));
        getTile(7, 7).placePiece(new Pawn(0));
        getTile(14, 7).placePiece(new Pawn(0));
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
    public void possibleMovesPawn1() {
        SelectEvent selectEvent = new SelectEvent(game, new BoardCoordinate(6, 0));
        emitAndSimulateRelay(selectEvent);

        assertEquals(game.getPossibleMovesCoordinates().size(), 3);
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(7, 0)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(7, 1)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(8, 2)));
    }

    @Test
    public void possibleMovesPawn2() {
        SelectEvent selectEvent = new SelectEvent(game, new BoardCoordinate(7, 7));
        emitAndSimulateRelay(selectEvent);

        assertEquals(game.getPossibleMovesCoordinates().size(), 4);
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(8, 7)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(9, 7)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(8, 8)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(9, 9)));
    }

    @Test
    public void possibleMovesPawn3() {
        SelectEvent selectEvent = new SelectEvent(game, new BoardCoordinate(14, 7));
        emitAndSimulateRelay(selectEvent);

        assertEquals(game.getPossibleMovesCoordinates().size(), 0);
    }

    public void emitAndSimulateRelay(SelectEvent selectEvent) {
        // Simulate relay
        selectEvent = new SelectEvent(selectEvent);
        selectEvent.emit();
    }
}
