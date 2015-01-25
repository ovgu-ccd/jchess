package tests.game;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import jchess.eventbus.events.SelectEvent;
import jchess.game.Game;
import jchess.game.IOSystem;
import jchess.game.board.Board;
import jchess.game.pieces.Pawn;
import jchess.game.pieces.Piece;
import jchess.util.BoardCoordinate;
import jchess.util.Logging;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by robert on 24/01/15.
 */
public class PawnMovementTest {

    @Singleton
    static class PawnMovementBoard extends Board {
        @Override
        protected void initFigures() {
            getTile(6, 0).placePiece(new Pawn(0));
            getTile(7, 7).placePiece(new Pawn(0));
            getTile(14, 7).placePiece(new Pawn(0));

            getTile(9, 8).placePiece(new Pawn(1));
            getTile(8, 6).placePiece(new Pawn(1));
            getTile(8, 9).placePiece(new Pawn(1));
        }
    }

    private Injector injector;
    private Game game;

    public PawnMovementTest() {
        Logging.setup();
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Board.class).to(PawnMovementBoard.class);
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

        assertEquals(game.getPossibleMovesCoordinates().size(), 7);
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(8, 7)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(9, 7)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(8, 8)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(9, 9)));

        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(9, 8)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(8, 6)));
        assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(8, 9)));
    }

    @Test
    public void movePawn2() {
        SelectEvent selectEvent = new SelectEvent(game, new BoardCoordinate(7, 7));
        emitAndSimulateRelay(selectEvent);

        Piece selectedPiece = game.getBoard().getTile(7, 7).getPiece();

        selectEvent = new SelectEvent(game, new BoardCoordinate(8, 7));
        emitAndSimulateRelay(selectEvent);


        assertNull(game.getBoard().getTile(7, 7).getPiece());
        assertEquals(game.getBoard().getTile(8, 7).getPiece(), selectedPiece);
    }

    @Test
    public void strikePawn2() {
        SelectEvent selectEvent = new SelectEvent(game, new BoardCoordinate(7, 7));
        emitAndSimulateRelay(selectEvent);

        Piece selectedPiece = game.getBoard().getTile(7, 7).getPiece();

        selectEvent = new SelectEvent(game, new BoardCoordinate(9, 8));
        emitAndSimulateRelay(selectEvent);


        assertNull(game.getBoard().getTile(7, 7).getPiece());
        assertEquals(game.getBoard().getTile(9, 8).getPiece(), selectedPiece);
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
        game.handleSelectEvent(selectEvent);
    }
}
