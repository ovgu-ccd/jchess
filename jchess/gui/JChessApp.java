/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jchess.gui;

import jchess.JChessView;
import jchess.Application;

import java.util.ResourceBundle;

/**
 * The main class of the application.
 */
public class JChessApp extends Application {
    private static JChessView jcv;

    public static JChessView getJcv() {
        return jcv;
    }

    public static void setJcv(JChessView jcv) {
        JChessApp.jcv = jcv;
    }

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        setJcv(new JChessView(this));
        show(getJcv());
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("jchess.resources.main");
        String title = resourceBundle.getString("Application.title");
        this.getMainFrame().setTitle(title);
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of JChessApp
     */
    public static JChessApp getApplication() {
        return Application.getInstance(JChessApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {

        launch(JChessApp.class, args);
    }
}
