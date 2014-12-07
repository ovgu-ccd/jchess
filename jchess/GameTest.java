package jchess;

import jchess.gui.GUIConnector;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    @Test(expected=IllegalArgumentException.class)
    public void testNewGameWrongNumberOfIOSystems() throws Exception {
        IOSystem[] systems = new IOSystem[]{null, null};
        Game.newGame(systems);
    }
}