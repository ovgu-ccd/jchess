package jchess.mvc.events;

import jchess.IOSystem;

/**
 * Created by robert on 09/01/15.
 */
public class NewGameEvent extends Event {

    private final IOSystem[] ioSystems;
    private final String[] playerNames;

    public NewGameEvent(String[] playerNames, IOSystem[] ioSystems){
        this.playerNames = playerNames;
        this.ioSystems = ioSystems;
    }


    public IOSystem[] getIoSystems() {
        return ioSystems;
    }

    public String[] getPlayerNames() {
        return playerNames;
    }
}
