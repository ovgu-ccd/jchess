package jchess;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import jchess.configurations.GameConfiguration;
import jchess.eventbus.Controller;
import jchess.eventbus.events.NewGameEvent;
import jchess.game.Game;
import jchess.gui.GUI;
import jchess.util.Logging;
import net.engio.mbassy.listener.Handler;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by robert on 04.12.14.
 */
@Singleton
public class Application implements Runnable {
    @Inject
    private Injector injector;
    @Inject
    private GUI gui;

    private List<Game> games;

    @Inject
    private Application() {
        Logging.setup();

        Controller.INSTANCE.subscribe(this);
        games = new LinkedList<>();
    }

    public static void main(String args[]) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        Injector injector = Guice.createInjector(new GameConfiguration());
        Application app = injector.getInstance(Application.class);
        SwingUtilities.invokeLater(app);
    }

    public void run() {
        gui.setVisible(true);
    }

    @Handler
    public void handleNewGame(NewGameEvent newGameEvent) {
        Logging.GAME.debug("Created new game.");
        Game game = injector.getInstance(Game.class);
        game.initializeGame(newGameEvent.getPlayerNames(), newGameEvent.getIoSystems());
        newGameEvent.getGameTab().setGame(game);
        games.add(game);
        game.emitUpdateBoardEvent();
    }


}
