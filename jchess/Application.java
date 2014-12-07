package jchess;

/**
 * Created by Severin Orth on 07.12.14.
 *
 * Main Entry point for the application
 *
 * Starts the event lib and ensures any creation of singletons (like GUI)
 */
public class Application {

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        JChessApp.launch(JChessApp.class, args);
    }

}
