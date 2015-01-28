package jchess.eventbus.events;

import jchess.game.IOSystem;
import jchess.gui.GameTab;

/**
 * Creating a game
 * <p/>
 * Created by robert on 09/01/15.
 *
 * @trace [$REQ07]
 * @flow GUI -> GL
 */
public class NewGameEvent extends Event {

    private final IOSystem[] ioSystems;
    private final String[] playerNames;
    private final GameTab gameTab;

    public NewGameEvent(String[] playerNames, IOSystem[] ioSystems, GameTab gameTab) {
        this.playerNames = playerNames;
        this.ioSystems = ioSystems;
        this.gameTab = gameTab;
    }

    /**
     * Getter for the IOSystems assigned with this event.
     * @return Array of IOSystems
     */
    public IOSystem[] getIoSystems() {
        return ioSystems;
    }

    /**
     * Getter for the player names assigned with this event.
     * @return Array of Strings with player names
     */
    public String[] getPlayerNames() {
        return playerNames;
    }

    /**
     * Getter for the GameTab assigned with this event.
     * @return GameTab
     */
    public GameTab getGameTab() {
        return gameTab;
    }
}
