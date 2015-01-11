package jchess.tests;

/**
 * Created by Elliot on 2014-12-07.
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import jchess.Board;


public class BoardTest {

    private Board board;

    @Before
    public void setup(){
        board = new Board(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetTile() {
        board.getTile(0,1);
    }

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
