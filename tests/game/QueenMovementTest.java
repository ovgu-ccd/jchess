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
import jchess.game.pieces.*;
import jchess.game.pieces.Queen;
import jchess.util.BoardCoordinate;
import jchess.util.Logging;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by elliot on 24/01/15.
 */

public class QueenMovementTest {

    @Singleton
    public static class QueenMovementBoard extends Board {
        @Override
        protected void initFigures() throws InvalidBoardCoordinateException {
            getTile(0, 0).placePiece(new Queen(0));
            getTile(12, 6).placePiece(new Bishop(0));
            getTile(5, 10).placePiece(new Bishop(1));
            getTile(6, 0).placePiece(new Pawn(0));
            getTile(0, 6).placePiece(new Pawn(0));
            getTile(13, 13).placePiece(new Pawn(1));
        }
    }

    private final Injector injector;

    private Game game;

    public QueenMovementTest() throws InvalidBoardCoordinateException {
        Logging.setup();
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Board.class).to(QueenMovementBoard.class);
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
    public void possibleMovesQueen1() {
        SelectEvent selectEvent = new SelectEvent(game, new BoardCoordinate(0, 0));
        emitAndSimulateRelay(selectEvent);

        assertEquals(33, game.getPossibleMovesCoordinates().size());
        for( int i = 1 ; i < 5 ; ++i ) {
            assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(2*i, i)));
            assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(i, 2*i)));
        }
        for (int i = 1; i < 14; i++) {
            assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(i, i)));
        }
        for (int i = 1; i < 6; i++) {
            assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(i, 0)));
            assertTrue(game.getPossibleMovesCoordinates().contains(new BoardCoordinate(0, i)));
        }
    }

    @Test
    public void moveQueen2() throws InvalidBoardCoordinateException {
        SelectEvent selectEvent = new SelectEvent(game, new BoardCoordinate(7, 7));
        emitAndSimulateRelay(selectEvent);

        Piece selectedPiece = game.getBoard().getTile(7, 7).getPiece();

        selectEvent = new SelectEvent(game, new BoardCoordinate(8, 7));
        emitAndSimulateRelay(selectEvent);


        assertNull(game.getBoard().getTile(7, 7).getPiece());
        assertEquals(selectedPiece, game.getBoard().getTile(8, 7).getPiece());
    }

    @Test
    public void strikeQueen2() throws InvalidBoardCoordinateException{
        SelectEvent selectEvent = new SelectEvent(game, new BoardCoordinate(7, 7));
        emitAndSimulateRelay(selectEvent);

        Piece selectedPiece = game.getBoard().getTile(7, 7).getPiece();

        selectEvent = new SelectEvent(game, new BoardCoordinate(9, 8));
        emitAndSimulateRelay(selectEvent);


        assertNull(game.getBoard().getTile(7, 7).getPiece());
        assertEquals(selectedPiece, game.getBoard().getTile(9, 8).getPiece());
    }


    @Test
    public void possibleMovesQueen3() {
        SelectEvent selectEvent = new SelectEvent(game, new BoardCoordinate(14, 7));
        emitAndSimulateRelay(selectEvent);

        assertEquals(0, game.getPossibleMovesCoordinates().size());
    }

    void emitAndSimulateRelay(SelectEvent selectEvent) {
        // Simulate relay
        selectEvent = new SelectEvent(selectEvent);
        game.handleSelectEvent(selectEvent);
    }
}
