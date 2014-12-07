package jchess;

import jchess.mvc.Controller;
import jchess.mvc.events.NewGame;
import net.engio.mbassy.listener.Handler;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Severin Orth on 07.12.14.
 *
 * Main Entry point for the application
 *
 * Starts the event lib and ensures any creation of singletons (like GUI)
 */
public class Application {
    private final List<Game> games      = new LinkedList<>();
    private final Chat       chat       = new Chat();
    private final Controller controller = Controller.INSTANCE;


    private Application(String[] args) {
        JChessApp.launch(JChessApp.class, args);

        controller.subscribe(this);
        controller.subscribe(chat);
    }


    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        new Application(args);
    }


    @Handler public void handleCreateGame(NewGame newGameEvent) {
        this.games.add(newGameEvent.getGame());
        controller.subscribe(newGameEvent.getGame());
    }

}
