package jchess;

import org.junit.Test;

public class GameTest {

    @Test(expected=IllegalArgumentException.class)
    public void testNewGameWrongNumberOfIOSystems() throws Exception {
        IOSystem[] systems = new IOSystem[]{null, null};
        Game.newGame(systems);
    }
}