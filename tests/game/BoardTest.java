package tests.game;

/**
 * Test for the GL board logic
 *
 * Created by Elliot on 2014-12-07.
 */

import jchess.game.board.Board;
import jchess.game.board.DefaultBoard;
import org.junit.Before;


class BoardTest {

    @Before
    public void setup() {
        Board board = new DefaultBoard();
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
