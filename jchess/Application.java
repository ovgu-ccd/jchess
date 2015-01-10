package jchess;


import jchess.gui.GUI;
import jchess.mvc.Controller;
import jchess.mvc.events.NewGameEvent;
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
        Application app = Application.getInstance();
        SwingUtilities.invokeLater(app);
    }

    @Handler
    public void handleNewGame(NewGameEvent newGameEvent) {
        Player[] players = new Player[3];

        players[0] = new Player(newGameEvent.getPlayerNames()[0],
                newGameEvent.getIoSystems()[0],
                "white");
        players[1] = new Player(newGameEvent.getPlayerNames()[1],
                newGameEvent.getIoSystems()[1],
                "black");
        players[2] = new Player(newGameEvent.getPlayerNames()[2],
                newGameEvent.getIoSystems()[2],
                "red");

        players[0].setActive(true);

        newGameEvent.getIoSystems()[0].setPlayer(players[0]);
        newGameEvent.getIoSystems()[1].setPlayer(players[1]);
        newGameEvent.getIoSystems()[2].setPlayer(players[2]);

        Logging.GAME.debug("Created new game.");
        Game game = Game.newGame(players);
        newGameEvent.getGameTab().setGame(game);
        games.add(game);
    }
}
