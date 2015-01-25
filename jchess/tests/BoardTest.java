package jchess.tests;

/**
 * Created by Elliot on 2014-12-07.
 */

import jchess.game.board.Board;
import jchess.game.board.DefaultBoard;
import org.junit.Before;


public class BoardTest {

    private Board board;

    @Before
    public void setup() {
        board = new DefaultBoard();
    }

    /*@Test(expected = IllegalArgumentException.class)
    public void testGetTile() {
        fail();
    }

    @Test
    public void testGetTile() {
        assert( board.getTile(  0,  0 ) == board.getTile(  0));
        assert( board.getTile(  0,  7 ) == board.getTile(  7));
        assert( board.getTile(  1,  0 ) == board.getTile(  8));
        assert( board.getTile(  4,  4 ) == board.getTile( 42));
        assert( board.getTile(  8,  5 ) == board.getTile( 96));
        assert( board.getTile( 11, 12 ) == board.getTile(139));
        assert( board.getTile( 11, 13 ) == board.getTile(140));
        assert( board.getTile( 12, 11 ) == board.getTile(148));
        assert( board.getTile( 13,  6 ) == board.getTile(152));
        assert( board.getTile( 14, 14 ) == board.getTile(168));
    }

    @Test
    public void testUndo() {
        boolean undo = board.undo();
        assertTrue(undo);
    }

    @Test
    public void testRedo() {
        assertFalse(board.redo());
    }*/
}
