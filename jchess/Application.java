package jchess;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import jchess.configurations.DefaultGameConfiguration;
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
 * DoUC JChess Main Entry Point. Creates injector and launches GUI.
 * Created by robert on 04.12.14.
 */
@Singleton
public class Application implements Runnable {
    @SuppressWarnings("UnusedDeclaration")
    @Inject
    private Injector injector;
    @SuppressWarnings("UnusedDeclaration")
    @Inject
    private GUI gui;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final List<Game> games;

    @Inject
    private Application() {
        Logging.setup();

        Controller.INSTANCE.subscribe(this);
        games = new LinkedList<>();
    }

    public static void main(String args[]) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        Injector injector = Guice.createInjector(new DefaultGameConfiguration());
        Application app = injector.getInstance(Application.class);
        SwingUtilities.invokeLater(app);
    }

    public void run() {
        gui.setVisible(true);
    }

    @SuppressWarnings("UnusedDeclaration")
    @Handler
    public void handleNewGame(NewGameEvent newGameEvent) {
        Logging.GAME.debug("Created new game.");
        Game game = injector.getInstance(Game.class);
        newGameEvent.getGameTab().setGame(game);
        game.initializeGame(newGameEvent.getPlayerNames(), newGameEvent.getIoSystems());
        games.add(game);
    }


}
