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

import jchess.*;
import jchess.mvc.events.NewGameEvent;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

/**
 * The application's main frame.
 */
public class GUI extends JFrame implements ActionListener {
    public JDialog newGameFrame;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JTabbedPane gamesPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newGameItem;

    private AboutBox aboutBox;
    private JMenu fileMenu;
    private JMenuItem exitMenuItem;
    private JMenu helpMenu;
    private JMenuItem aboutMenuItem;


    public GUI(Application application) {
        super();

        setMinimumSize(new Dimension(500, 500));
        setLayout(new BorderLayout());

        initComponents();


        Logging.GUI.debug("Gui created.");
    }

    public void createNewGame(String firstName,
                              String secondName,
                              String thirdName) {
        GameTab newTab = new GameTab();
        this.gamesPane.addTab(firstName + " vs " + secondName + " vs " + thirdName, newTab);


        newGameFrame.setVisible(false);
        newTab.boardView.repaint();

        NewGameEvent event = new NewGameEvent(
                new String[]{firstName, secondName, thirdName},
                new IOSystem[]{
                        new GUIConnector(),
                        new GUIConnector(),
                        new GUIConnector()
                }, newTab);

        pack();
        Logging.GUI.debug("GUI: Emit NewGameEvent");
        event.emit();
    }

    public GameTab addNewTab(String title) {
        GameTab newGUI = new GameTab();
        this.gamesPane.addTab(title, newGUI);
        return newGUI;
    }


    void showAboutBox() {
        if (aboutBox == null) {
            aboutBox = new AboutBox();
            aboutBox.pack();
            aboutBox.setLocationRelativeTo(this);
        }
        aboutBox.setVisible(true);
    }


    public void actionPerformed(ActionEvent event) {
        Object target = event.getSource();
        if (target == newGameItem) {
            newGameFrame = new NewGameWindow(this);
            newGameFrame.setLocationRelativeTo(this);
            newGameFrame.setVisible(true);
        }
    }


    private void initComponents() {
        setTitle(StringResources.MAIN.getString("Application.title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new javax.swing.JPanel();
        gamesPane = new JChessTabbedPane();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new JMenu();
        newGameItem = new javax.swing.JMenuItem();
        exitMenuItem = new JMenuItem();
        gameMenu = new javax.swing.JMenu();
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

        gameMenu.setText(StringResources.GUI.getString("gameMenu.text")); // NOI18N
        gameMenu.setName("gameMenu"); // NOI18N

        menuBar.add(gameMenu);

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
