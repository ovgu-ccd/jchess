package jchess.mvc.events;

import jchess.Board;
import jchess.Game;
import jchess.Settings;

/**
 * Created by Severin Orth on 07.12.14.
 *
 * NewGame message for creating a new game
 */
public class UpdateBoardEvent extends Event {

    private final Game      game;


    public UpdateBoardEvent(Game game) {
        super();

        this.game = game;
    }

    public Game getGame() {
        return game;
    }

}
