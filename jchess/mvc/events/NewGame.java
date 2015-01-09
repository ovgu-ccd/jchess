package jchess.mvc.events;

import jchess.Game;
import jchess.Settings;

/**
 * Created by Severin Orth on 07.12.14.
 *
 * NewGame message for creating a new game
 */
public class NewGame extends Event {

    private final Settings settings;
    private final Game     game;


    public NewGame(Game game, Settings settings) {
        super();

        this.game = game;
        this.settings = settings;
    }


    public Game getGame() {
        return game;
    }
}
