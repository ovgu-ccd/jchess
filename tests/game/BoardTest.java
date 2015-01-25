package tests.game;

/**
 * Test for the GL board logic
 *
 * Created by Elliot on 2014-12-07.
 */

import jchess.game.board.Board;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class BoardTest {

    private Board board;

    @Before
    public void setup() {
        board = new Board();
    }

    /*@Test(expected = IllegalArgumentException.class)
    public void testGetTile() {
        fail();
    }*/

    @Test
    public void testUndo() {
        boolean undo = board.undo();
        assertTrue(undo);
    }

    @Test
    public void testRedo() {
        assertFalse(board.redo());
    }
}
