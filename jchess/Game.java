package jchess;

import jchess.gui.BoardView;

/**
 * Created by andreas on 07.12.14.
 */
public class Game {
    private IOSystem[] ioSystems;

    private Game(IOSystem[] ioSystems){
        this.ioSystems = ioSystems;
    }


    public static Game newGame(IOSystem[] ioSystems) throws  IllegalArgumentException{
        if (ioSystems.length != 3){
            throw new IllegalArgumentException();
        }

        return new Game(ioSystems);
    }
}
