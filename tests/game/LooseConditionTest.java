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
import jchess.game.pieces.Piece;
import jchess.game.pieces.Rook;
import jchess.util.BoardCoordinate;
import jchess.util.Logging;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by robert on 24/01/15.
 * [$REQ40b]
 */

public class LooseConditionTest {

    @Singleton
    public static class LooseConditionBoard extends Board {
        @Override
        protected void initFigures() throws InvalidBoardCoordinateException {
            getTile(0, 0).placePiece(new Rook(0));
            getTile(7, 7).placePiece(new King(1));
            getTile(14, 14).placePiece(new Pawn(1));
        }
    }

    private final Injector injector;

    private Game game;

    public LooseConditionTest() {
        Logging.setup();
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Board.class).to(LooseConditionBoard.class);
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
    public void possibleMovesKing() throws InvalidBoardCoordinateException {
        SelectEvent selectEvent = new SelectEvent(game, new BoardCoordinate(0, 0));
        emitAndSimulateRelay(selectEvent);

        Piece selectedPiece = game.getBoard().getTile(0, 0).getPiece();

        selectEvent = new SelectEvent(game, new BoardCoordinate(7, 7));
        emitAndSimulateRelay(selectEvent);

        assertEquals(selectedPiece, game.getBoard().getTile(7, 7).getPiece());
        assertNull(game.getBoard().getTile(14, 14).getPiece());
    }


    void emitAndSimulateRelay(SelectEvent selectEvent) {
        // Simulate relay
        selectEvent = new SelectEvent(selectEvent);
        game.handleSelectEvent(selectEvent);
    }
}
