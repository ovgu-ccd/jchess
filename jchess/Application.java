package jchess;

import jchess.gui.JChessView;

/**
 * Created by robert on 04.12.14.
 */
public class Application {
    private static Application app;
    private JChessView jcv;

    private Application() {
        jcv = new JChessView();
    }

    public static Application getInstance() {
        if (app == null) {
            app = new Application();
        }
        return app;
    }

    public void run() {
        jcv.setVisible(true);
    }

    public JChessView getJcv() {
        return jcv;
    }

    public static void main(String args[]) {
        Application app = Application.getInstance();
        app.run();
    }
}
