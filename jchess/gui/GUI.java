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

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import jchess.eventbus.events.NewGameEvent;
import jchess.game.IOSystem;
import jchess.util.Logging;
import jchess.util.StringResources;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The application's main frame.
 *
 * @trace [$REQ31]
 */
@Singleton
public class GUI extends JFrame implements ActionListener {
    @SuppressWarnings("UnusedDeclaration")
    @Inject
    private Injector injector;
    private NewGameDialog newGameDialog;

    private TabbedPaneWithCreateButton gamesPane;
    private JMenuBar menuBar;
    private JMenuItem newGameItem;

    private AboutBox aboutBox;
    private JMenu fileMenu;
    private JMenuItem exitMenuItem;
    private JMenu helpMenu;
    private JMenuItem aboutMenuItem;


    @SuppressWarnings("UnusedDeclaration")
    public GUI() {
        super();

        setMinimumSize(new Dimension(500, 500));
        setLayout(new BorderLayout());

        initComponents();
        Logging.GUI.debug("Gui created.");
    }

    /**
     * Create new GameTab and emit NewGameEvent.
     *
     * @param firstName  First player name
     * @param secondName Second player name
     * @param thirdName  Third player name
     */
    public void createNewGame(String firstName,
                              String secondName,
                              String thirdName) {
        GameTab newTab = injector.getInstance(GameTab.class);
        this.gamesPane.addTab(firstName + " vs " + secondName + " vs " + thirdName, newTab);

        newGameDialog.setVisible(false);
        newTab.getBoardView().repaint();

        NewGameEvent event = new NewGameEvent(
                new String[]{firstName, secondName, thirdName},
                new IOSystem[]{
                        injector.getInstance(GUIConnector.class),
                        injector.getInstance(GUIConnector.class),
                        injector.getInstance(GUIConnector.class)
                }, newTab);

        pack();
        Logging.GUI.debug("GUI: Emit NewGameEvent");
        event.emit();
    }

    /**
     * This method creates and shows the AboutBox.
     */
    void showAboutBox() {
        if (aboutBox == null) {
            aboutBox = new AboutBox();
            aboutBox.pack();
            aboutBox.setLocationRelativeTo(this);
        }
        aboutBox.setVisible(true);
    }

    /**
     * Handler for creating and showing the NewGameDialog.
     *
     * @param event ActionEvent which triggered the handler
     */
    public void actionPerformed(ActionEvent event) {
        Object target = event.getSource();
        if (target == newGameItem) {
            showNewGameDialog();
        }
    }

    public void showNewGameDialog() {
        newGameDialog = new NewGameDialog(this);
        newGameDialog.setLocationRelativeTo(this);
        newGameDialog.setVisible(true);
    }


    private void initComponents() {
        setTitle(StringResources.MAIN.getString("Application.title"));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        gamesPane = new TabbedPaneWithCreateButton(this);
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        newGameItem = new JMenuItem();
        exitMenuItem = new JMenuItem();
        helpMenu = new JMenu();
        aboutMenuItem = new JMenuItem();

        createMenus();

        mainPanel.setName("mainPanel");
        mainPanel.setLayout(new BorderLayout());


        gamesPane.setName("gamesPane");
        mainPanel.add(gamesPane, BorderLayout.CENTER);


        add(mainPanel, BorderLayout.CENTER);
        pack();
    }

    private void createMenus() {
        menuBar.setName("menuBar");
        fileMenu.setText(StringResources.GUI.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        newGameItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newGameItem.setText(StringResources.GUI.getString("newGameItem.text")); // NOI18N
        newGameItem.setName("newGameItem"); // NOI18N
        fileMenu.add(newGameItem);
        newGameItem.addActionListener(this);

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }); // NOI18N
        exitMenuItem.setText(StringResources.GUI.getString("file.quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(StringResources.GUI.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                showAboutBox();
            }
        }); // NOI18N
        aboutMenuItem.setText(StringResources.GUI.getString("help.about"));
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

}
