package jchess;

import jchess.game.Game;
import jchess.gui.GUI;
import jchess.eventbus.Controller;
import jchess.eventbus.events.NewGameEvent;
import jchess.util.Logging;
import net.engio.mbassy.listener.Handler;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by robert on 04.12.14.
 */
public class Application implements Runnable {
    private static Application instance;
    private final Controller controller = Controller.INSTANCE;
    private GUI gui;
    private List<Game> games;

    private Application() {
        Logging.setup();
        controller.subscribe(this);

        games = new LinkedList<>();

        gui = new GUI(this);
    }

    public static Application getInstance() {
        if (Application.instance == null) {
            Application.instance = new Application();
        }

        return Application.instance;
    }

    public void run() {
        gui.setVisible(true);
    }

    public GUI getGUI() {
        return gui;
    }


    public static void main(String args[]) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        Application app = Application.getInstance();
        SwingUtilities.invokeLater(app);
    }

    @Handler
    public void handleNewGame(NewGameEvent newGameEvent) {

        Logging.GAME.debug("Created new game.");
        Game game = Game.newGame(newGameEvent.getPlayerNames(), newGameEvent.getIoSystems());
        newGameEvent.getGameTab().setGame(game);
        games.add(game);
        game.emitUpdateBoardEvent();
    }
}
