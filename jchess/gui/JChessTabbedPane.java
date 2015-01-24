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
#    along with this program.  If not, see <http://www.gnu.org/licenses/>..
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package jchess.gui;

import com.google.inject.Inject;
import jchess.Application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class JChessTabbedPane extends JTabbedPane implements MouseListener, ImageObserver {
    @Inject
    private GUI gui;
    @Inject
    private NewGameDialog newGameDialog;
    private TabbedPaneIcon closeIcon;
    private Image addIcon = null;
    private Rectangle addIconRect = null;

    public JChessTabbedPane() {
        super();
        this.closeIcon = new TabbedPaneIcon(this.closeIcon);
        try {
            this.addIcon = ImageIO.read(Application.class.getResource("images.org/add-tab-icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setDoubleBuffered(true);
        super.addMouseListener(this);
    }

    @Override
    public void addTab(String title, Component component) {
        this.addTab(title, component, null);
    }

    void addTab(String title, Component component, Icon closeIcon) {
        super.addTab(title, new TabbedPaneIcon(closeIcon), component);
        System.out.println("Present number of tabs: " + this.getTabCount());
        this.updateAddIconRect();
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    private void showNewGameWindow() {
        newGameDialog.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Rectangle rect;
        int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());
        if (tabNumber >= 0) {
            rect = ((TabbedPaneIcon) getIconAt(tabNumber)).getBounds();
            if (rect.contains(e.getX(), e.getY())) {
                System.out.println("Removing tab with " + tabNumber + " number!...");
                this.removeTabAt(tabNumber);//remove tab
                this.updateAddIconRect();
            }
            if (this.getTabCount() == 0) {
                this.showNewGameWindow();
            }
        } else if (this.addIconRect != null && this.addIconRect.contains(e.getX(), e.getY())) {
            System.out.println("newGame by + button");
            this.showNewGameWindow();
        }
        //System.out.println("x:" +e.getX()+" y: "+e.getY()+" x:"+this.addIconRect.x+" y::"+this.addIconRect.y+" width:"+this.addIconRect.width+" height: "+this.addIconRect.height);
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    private void updateAddIconRect() {
        if (this.getTabCount() > 0) {
            Rectangle rect = this.getBoundsAt(this.getTabCount() - 1);
            this.addIconRect = new Rectangle(rect.x + rect.width + 5, rect.y, this.addIcon.getWidth(this), this.addIcon.getHeight(this));
        } else {
            this.addIconRect = null;
        }
    }

    private Rectangle getAddIconRect() {
        return this.addIconRect;
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        super.imageUpdate(img, infoflags, x, y, width, height);
        this.updateAddIconRect();
        return true;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Rectangle rect = this.getAddIconRect();
        if (rect != null) {
            g.drawImage(this.addIcon, rect.x, rect.y, null);
        }
    }

    @Override
    public void update(Graphics g) {
        this.repaint();
    }
}

