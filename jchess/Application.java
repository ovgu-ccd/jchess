package jchess;


import jchess.gui.GUI;
import jchess.mvc.Controller;
import jchess.mvc.events.NewGame;
import net.engio.mbassy.listener.Handler;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by robert on 04.12.14.
 */
public class Application {
    private static Application instance;
    private GUI gui;
    private final Controller controller = Controller.INSTANCE;
    private List<Game> games;

    private Application() {
        games = new LinkedList<>();
        controller.subscribe(this);

        Logging.setup();
        gui = new GUI(this);
    }

    public static Application getInstance() {
        if (Application.instance == null) {
            Application.instance = new Application();
        }

        return Application.instance;
    }

    void run() {
        gui.setVisible(true);
    }

    public GUI getJcv() {
        return gui;
    }

    public void createGame(IOSystem[] ioSystem) {
        Game.newGame(ioSystem);
    }

    public static void main(String args[]) {
        Application app = Application.getInstance();
        app.run();
    }

    @Handler
    public void handleCreateGame(NewGame newGameEvent) {
        this.games.add(newGameEvent.getGame());
        controller.subscribe(newGameEvent.getGame());
    }
}
