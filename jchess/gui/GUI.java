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
    private static GUIUtils gui = null;
    // End of variables declaration//GEN-END:variables
    //private JTabbedPaneWithIcon gamesPane;
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    public JDialog newGameFrame;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JTabbedPane gamesPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newGameItem;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JProgressBar progressBar;

    private javax.swing.JMenuItem saveGameItem;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JMenuItem themeSettingsMenu;
    private int busyIconIndex = 0;
    private AboutBox aboutBox;
    private JMenu fileMenu;
    private JMenuItem exitMenuItem;
    private JMenu helpMenu;
    private JMenuItem aboutMenuItem;
    private JSeparator statusPanelSeparator;


    public GUI(Application application) {
        super();

        setMinimumSize(new Dimension(500, 500));
        setLayout(new BorderLayout());

        initComponents();
        // status bar initialization - message timeout, idle icon and busy animation, etc
        int messageTimeout = Integer.parseInt(StringResources.GUI.getString("StatusBar.messageTimeout"));
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = Integer.parseInt(StringResources.GUI.getString("StatusBar.busyAnimationRate"));
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = new ImageIcon("resource/" + StringResources.GUI.getString("StatusBar.busyIcons[" + i + "]"));
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = new ImageIcon("resource/" + StringResources.GUI.getString("StatusBar.idleIcon"));
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor

        statusPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });


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
        } else if (target == saveGameItem) {
            //saveGame
            if (this.gamesPane.getTabCount() == 0) {
                JOptionPane.showMessageDialog(null, StringResources.MAIN.getString("save_not_called_for_tab"));
                return;
            }
            while (true) {
                //until
                JFileChooser fc = new JFileChooser();
                int retVal = fc.showSaveDialog(this.gamesPane);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File selFile = fc.getSelectedFile();
                    GameTab tempGUI = (GameTab) this.gamesPane.getComponentAt(this.gamesPane.getSelectedIndex());
                    if (!selFile.exists()) {
                        try {
                            selFile.createNewFile();
                        } catch (java.io.IOException exc) {
                            System.out.println("error creating file: " + exc);
                        }
                    } else if (selFile.exists()) {
                        int opt = JOptionPane.showConfirmDialog(tempGUI, StringResources.MAIN.getString("file_exists"), StringResources.MAIN.getString("file_exists"), JOptionPane.YES_NO_OPTION);
                        if (opt == JOptionPane.NO_OPTION) { //if user choose to now overwrite
                            continue; // go back to file choose
                        }
                    }
                    System.out.println(fc.getSelectedFile().isFile());
                    break;
                } else if (retVal == JFileChooser.CANCEL_OPTION) {
                    break;
                }
                ///JChessView.gui.game.saveGame(fc.);
            }
        } else if (target == this.themeSettingsMenu) {
            try {
                ThemeChooseWindow choose = new ThemeChooseWindow(this);
                choose.setVisible(true);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(
                        this,
                        exc.getMessage()
                );
                System.out.println("Something wrong creating window - perhaps themeList is null");
            }
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
        saveGameItem = new javax.swing.JMenuItem();
        exitMenuItem = new JMenuItem();
        gameMenu = new javax.swing.JMenu();
        optionsMenu = new javax.swing.JMenu();
        themeSettingsMenu = new javax.swing.JMenuItem();
        helpMenu = new JMenu();
        aboutMenuItem = new JMenuItem();
        statusPanel = new javax.swing.JPanel();
        statusPanelSeparator = new JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        createMenus();

        mainPanel.setName("mainPanel");
        mainPanel.setLayout(new BorderLayout());


        gamesPane.setName("gamesPane");
        mainPanel.add(gamesPane, BorderLayout.CENTER);


        statusPanel.setName("statusPanel"); // NOI18N
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N


        add(mainPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
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

        saveGameItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveGameItem.setText(StringResources.GUI.getString("saveGameItem.text")); // NOI18N
        saveGameItem.setName("saveGameItem"); // NOI18N
        fileMenu.add(saveGameItem);
        saveGameItem.addActionListener(this);


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

        optionsMenu.setText(StringResources.GUI.getString("optionsMenu.text")); // NOI18N
        optionsMenu.setName("optionsMenu"); // NOI18N

        themeSettingsMenu.setText(StringResources.GUI.getString("themeSettingsMenu.text")); // NOI18N
        themeSettingsMenu.setName("themeSettingsMenu"); // NOI18N
        optionsMenu.add(themeSettingsMenu);
        themeSettingsMenu.addActionListener(this);

        menuBar.add(optionsMenu);

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


    GameTab getActiveTabGame() throws ArrayIndexOutOfBoundsException {
        return (GameTab) this.gamesPane.getComponentAt(this.gamesPane.getSelectedIndex());
    }

    public int getNumberOfOpenedTabs() {
        return this.gamesPane.getTabCount();
    }

}
