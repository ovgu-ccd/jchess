package jchess;

import jchess.gui.GUI;

/**
 * Created by robert on 04.12.14.
 */
public class Application {
    private static Application instance;
    private GUI gui;

    private Application() {
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

    public GUI getJcv() {
        return gui;
    }

    public void createGame(IOSystem ioSystem) {
        // send new game message to Game class
    }

    public static void main(String args[]) {
        Application app = Application.getInstance();
        app.run();
    }
}
