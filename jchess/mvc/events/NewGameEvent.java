package jchess.mvc.events;

import jchess.IOSystem;
import jchess.gui.GameTab;

/**
 * Created by robert on 09/01/15.
 */
public class NewGameEvent extends Event {

    private final IOSystem[] ioSystems;
    private final String[] playerNames;
    private GameTab gameTab;

    public NewGameEvent(String[] playerNames, IOSystem[] ioSystems, GameTab gameTab){
        this.playerNames = playerNames;
        this.ioSystems = ioSystems;
        this.gameTab = gameTab;
    }


    public IOSystem[] getIoSystems() {
        return ioSystems;
    }

    public String[] getPlayerNames() {
        return playerNames;
    }

    public GameTab getGameTab() {
        return gameTab;
    }
}
