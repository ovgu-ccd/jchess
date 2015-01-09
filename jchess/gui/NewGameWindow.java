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

/*
 * NewGameWindow.java
 *
 * Created on 2009-10-20, 15:11:49
 */
package jchess.gui;

import jchess.StringResources;

import javax.swing.*;

/**
 *
 * @author donmateo
 */
class NewGameWindow extends JDialog {

    private GUI parPtr;

    /** Creates new form NewGameWindow */
    public NewGameWindow(GUI parent) {
        super(parent);
        parPtr = parent;
        initComponents();

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.jTabbedPane1.addTab(StringResources.MAIN.getString("local_game"), new DrawLocalSettings(this));
        this.jTabbedPane1.addTab(StringResources.MAIN.getString("network_game"), new DrawNetworkSettings(this));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setName("Form"); // NOI18N
        setTitle(StringResources.GUI.getString("newGameWindow.title"));

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                      .addContainerGap()
                      .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                      .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                      .addGap(20, 20, 20)
                      .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                      .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /* relay method from apfohl */
    public void createNewGame(String firstName,
                              String secondName,
                              String thirdName) {
        parPtr.createNewGame(firstName,
                secondName,
                thirdName);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new NewGameWindow(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
