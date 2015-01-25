package jchess.tests.game;

/**
 * Created by Elliot on 2014-12-07.
 */

import jchess.game.board.Board;
import jchess.game.board.DefaultBoard;
import jchess.game.board.InvalidBoardCoordinateException;
import jchess.util.BoardCoordinate;
import org.junit.Before;
import org.junit.Test;


public class BoardTest {

    private Board board;

    @Before
    public void setup() throws InvalidBoardCoordinateException {
        board = new DefaultBoard();
    }

    @Test
    public void testGetTile() throws InvalidBoardCoordinateException {
        assert (board.getTile(0, 0) == board.getTile(0));
        assert (board.getTile(0, 7) == board.getTile(7));
        assert (board.getTile(1, 0) == board.getTile(8));
        assert (board.getTile(4, 4) == board.getTile(42));
        assert (board.getTile(8, 5) == board.getTile(96));
        assert (board.getTile(11, 12) == board.getTile(139));
        assert (board.getTile(11, 13) == board.getTile(140));
        assert (board.getTile(12, 11) == board.getTile(148));
        assert (board.getTile(13, 6) == board.getTile(152));
        assert (board.getTile(14, 14) == board.getTile(168));
    }


    @Test(expected=InvalidBoardCoordinateException.class)
    public void testGetTileNotOnBoard() throws InvalidBoardCoordinateException {
        BoardCoordinate bc = new BoardCoordinate( -1, -1 );
        int negIdx = bc.getI();
        assert( board.getTile(  negIdx ) == null );
        assert( board.getTile(  -1, -1 ) == null );
        assert (board.getTile(-1) == null);
        assert (board.getTile(169) == null);
    }

    @Test(expected=InvalidBoardCoordinateException.class)
    public void testGetTileNotOnBoard_byTileIndex_less_0() throws InvalidBoardCoordinateException {
        board.getTile(-1);
    }

    @Test(expected=InvalidBoardCoordinateException.class)
    public void testGetTileNotOnBoard_byTileIndex_greater_168() throws InvalidBoardCoordinateException {
        board.getTile(169);
    }

    @Test(expected=InvalidBoardCoordinateException.class)
    public void testGetTileNotOnBoard_byBoardCoordinates() throws InvalidBoardCoordinateException {
        board.getTile(-1,-1);
    }

    @Test(expected=InvalidBoardCoordinateException.class)
    public void testGetTileNotOnBoard_byBoardCoordinate_Class() throws InvalidBoardCoordinateException {
        BoardCoordinate bc = new BoardCoordinate( -1, -1 );
        board.getTile(bc);
    }
}
