package jchess.configurations;

import com.google.inject.AbstractModule;
import jchess.game.board.Board;
import jchess.game.board.DefaultBoard;

/**
 * Created by robert on 24/01/15.
 */
public class GameConfiguration extends AbstractModule {

    @Override
    protected void configure() {
        bind(Board.class).to(DefaultBoard.class);
    }
}
