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
    public void testUndo() {
        boolean undo = board.undo();
        assertTrue(undo);
    }

    @Test
    public void testRedo() {
        assertFalse(board.redo());
    }*/
}
