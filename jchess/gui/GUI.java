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

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;


/**
 * The application's main frame.
 */
public class GUI extends JFrame implements ActionListener, ComponentListener {
    static jchess.GUI gui = null;
    // End of variables declaration//GEN-END:variables
    //private JTabbedPaneWithIcon gamesPane;
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    public javax.swing.JPanel mainPanel;
    public JDialog newGameFrame;
    jchess.GUI activeGUI;//in future it will be reference to active tab
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu gameMenu;
    private javax.swing.JTabbedPane gamesPane;
    private javax.swing.JMenuItem loadGameItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem moveBackItem;
    private javax.swing.JMenuItem moveForwardItem;
    private javax.swing.JMenuItem newGameItem;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenuItem rewindToBegin;
    private javax.swing.JMenuItem rewindToEnd;
    private javax.swing.JMenuItem saveGameItem;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JMenuItem themeSettingsMenu;
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private PawnPromotionWindow promotionBox;
    private JMenu fileMenu;
    private JMenuItem exitMenuItem;
    private JMenu helpMenu;
    private JMenuItem aboutMenuItem;
    private JSeparator statusPanelSeparator;

    public GUI() {
        super();

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

    }

    public Game addNewTab(String title) {
        Game newGUI = new Game();
        this.gamesPane.addTab(title, newGUI);
        return newGUI;
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
                    Game tempGUI = (Game) this.gamesPane.getComponentAt(this.gamesPane.getSelectedIndex());
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
                    if (selFile.canWrite()) {
                        tempGUI.saveGame(selFile);
                    }
                    System.out.println(fc.getSelectedFile().isFile());
                    break;
                } else if (retVal == JFileChooser.CANCEL_OPTION) {
                    break;
                }
                ///JChessView.gui.game.saveGame(fc.);
            }
        } else if (target == loadGameItem) {
            //loadGame
            JFileChooser fc = new JFileChooser();
            int retVal = fc.showOpenDialog(this.gamesPane);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                if (file.exists() && file.canRead()) {
                    Game.loadGame(file);
                }
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

    public void showAboutBox() {
        if (aboutBox == null) {
            aboutBox = new JChessAboutBox(this);
            aboutBox.setLocationRelativeTo(this);
        }
        aboutBox.setVisible(true);
    }

    public String showPawnPromotionBox(String color) {
        if (promotionBox == null) {
            JFrame mainFrame = this;
            promotionBox = new PawnPromotionWindow(mainFrame, color);
            promotionBox.setLocationRelativeTo(mainFrame);
            promotionBox.setModal(true);

        }
        promotionBox.setColor(color);
        promotionBox.setVisible(true);

        return promotionBox.result;
    }

    public String showSaveWindow() {

        return "";
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {
        setTitle(StringResources.MAIN.getString("Application.title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new javax.swing.JPanel();
        gamesPane = new JChessTabbedPane();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new JMenu();
        newGameItem = new javax.swing.JMenuItem();
        loadGameItem = new javax.swing.JMenuItem();
        saveGameItem = new javax.swing.JMenuItem();
        exitMenuItem = new JMenuItem();
        gameMenu = new javax.swing.JMenu();
        moveBackItem = new javax.swing.JMenuItem();
        moveForwardItem = new javax.swing.JMenuItem();
        rewindToBegin = new javax.swing.JMenuItem();
        rewindToEnd = new javax.swing.JMenuItem();
        optionsMenu = new javax.swing.JMenu();
        themeSettingsMenu = new javax.swing.JMenuItem();
        helpMenu = new JMenu();
        aboutMenuItem = new JMenuItem();
        statusPanel = new javax.swing.JPanel();
        statusPanelSeparator = new JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setMaximumSize(new java.awt.Dimension(800, 600));
        mainPanel.setMinimumSize(new java.awt.Dimension(800, 600));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        gamesPane.setName("gamesPane"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(gamesPane, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(gamesPane, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(StringResources.GUI.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        newGameItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newGameItem.setText(StringResources.GUI.getString("newGameItem.text")); // NOI18N
        newGameItem.setName("newGameItem"); // NOI18N
        fileMenu.add(newGameItem);
        newGameItem.addActionListener(this);

        loadGameItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        loadGameItem.setText(StringResources.GUI.getString("loadGameItem.text")); // NOI18N
        loadGameItem.setName("loadGameItem"); // NOI18N
        fileMenu.add(loadGameItem);
        loadGameItem.addActionListener(this);

        saveGameItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
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

        moveBackItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        moveBackItem.setText(StringResources.GUI.getString("moveBackItem.text")); // NOI18N
        moveBackItem.setName("moveBackItem"); // NOI18N
        moveBackItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                moveBackItemMouseClicked(evt);
            }
        });
        moveBackItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveBackItemActionPerformed(evt);
            }
        });
        gameMenu.add(moveBackItem);

        moveForwardItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        moveForwardItem.setText(StringResources.GUI.getString("moveForwardItem.text")); // NOI18N
        moveForwardItem.setName("moveForwardItem"); // NOI18N
        moveForwardItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                moveForwardItemMouseClicked(evt);
            }
        });
        moveForwardItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveForwardItemActionPerformed(evt);
            }
        });
        gameMenu.add(moveForwardItem);

        rewindToBegin.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        rewindToBegin.setText(StringResources.GUI.getString("rewindToBegin.text")); // NOI18N
        rewindToBegin.setName("rewindToBegin"); // NOI18N
        rewindToBegin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rewindToBeginActionPerformed(evt);
            }
        });
        gameMenu.add(rewindToBegin);

        rewindToEnd.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        rewindToEnd.setText(StringResources.GUI.getString("rewindToEnd.text")); // NOI18N
        rewindToEnd.setName("rewindToEnd"); // NOI18N
        rewindToEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rewindToEndActionPerformed(evt);
            }
        });
        gameMenu.add(rewindToEnd);

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
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAboutBox();
            }
        }); // NOI18N
        aboutMenuItem.setText(StringResources.GUI.getString("help.about"));
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
                statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                        .addGroup(statusPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(statusMessageLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 616, Short.MAX_VALUE)
                                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(statusAnimationLabel)
                                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
                statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(statusPanelLayout.createSequentialGroup()
                                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(statusMessageLabel)
                                        .addComponent(statusAnimationLabel)
                                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3))
        );

        add(mainPanel);
        setJMenuBar(menuBar);
        add(statusPanel, BorderLayout.SOUTH);
        pack();
    }

    private void moveBackItemActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_moveBackItemActionPerformed
        //GEN-HEADEREND:event_moveBackItemActionPerformed
        if (gui != null && gui.game != null) {
            gui.game.undo();
        } else {
            try {
                Game activeGame = this.getActiveTabGame();
                if (!activeGame.undo()) {
                    JOptionPane.showMessageDialog(null, "Nie da sie cofnac!");
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException exc) {
                JOptionPane.showMessageDialog(null, "Brak aktywnej karty!");
            } catch (UnsupportedOperationException exc) {
                JOptionPane.showMessageDialog(null, exc.getMessage());
            }
        }

    }//GEN-LAST:event_moveBackItemActionPerformed

    private void moveBackItemMouseClicked(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_moveBackItemMouseClicked
        //GEN-HEADEREND:event_moveBackItemMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_moveBackItemMouseClicked

    private void moveForwardItemMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:

    }

    private void moveForwardItemActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        if (gui != null && gui.game != null) {
            gui.game.redo();
        } else {
            try {
                Game activeGame = this.getActiveTabGame();
                if (!activeGame.redo()) {
                    JOptionPane.showMessageDialog(null, "W pamieci brak ruchow do przodu!");
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException exc) {
                JOptionPane.showMessageDialog(null, "Brak aktywnej karty!");
            } catch (UnsupportedOperationException exc) {
                JOptionPane.showMessageDialog(null, exc.getMessage());
            }
        }
    }

    private void rewindToBeginActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            Game activeGame = this.getActiveTabGame();
            if (!activeGame.rewindToBegin()) {
                JOptionPane.showMessageDialog(null, "W pamieci brak ruchow do przodu!");
            }
        } catch (ArrayIndexOutOfBoundsException exc) {
            JOptionPane.showMessageDialog(null, "Brak aktywnej karty!");
        } catch (UnsupportedOperationException exc) {
            JOptionPane.showMessageDialog(null, exc.getMessage());
        }
    }

    private void rewindToEndActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            Game activeGame = this.getActiveTabGame();
            if (!activeGame.rewindToEnd()) {
                JOptionPane.showMessageDialog(null, "W pamieci brak ruchow wstecz!");
            }
        } catch (ArrayIndexOutOfBoundsException exc) {
            JOptionPane.showMessageDialog(null, "Brak aktywnej karty!");
        } catch (UnsupportedOperationException exc) {
            JOptionPane.showMessageDialog(null, exc.getMessage());
        }
    }

    public void componentResized(ComponentEvent e) {
        System.out.println("jchessView resized!!;");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected Game getActiveTabGame() throws ArrayIndexOutOfBoundsException {
        Game activeGame = (Game) this.gamesPane.getComponentAt(this.gamesPane.getSelectedIndex());
        return activeGame;
    }

    public int getNumberOfOpenedTabs() {
        return this.gamesPane.getTabCount();
    }

    public void componentMoved(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void componentShown(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void componentHidden(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
