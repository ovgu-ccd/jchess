package jchess.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @todo Document this
 */
public class TabbedPaneIcon implements Icon {

    private int x_pos;
    private int y_pos;
    private int width;
    private int height;
    private Icon fileIcon;

    public TabbedPaneIcon(Icon fileIcon) {
        this.fileIcon = fileIcon;
        width = 16;
        height = 16;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        this.x_pos = x;
        this.y_pos = y;

        Color col = g.getColor();

        g.setColor(Color.black);
        int y_p = y + 2;
        g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
        g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
        g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
        g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
        g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
        g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);
        g.setColor(col);
        if (fileIcon != null) {
            fileIcon.paintIcon(c, g, x + width, y_p);
        }
    }//--endOf-PaintIcon--

    public int getIconWidth() {
        return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);
    }//--endOf-getIconWidth--

    public int getIconHeight() {
        return height;
    }//--endOf-getIconHeight()--

    public Rectangle getBounds() {
        return new Rectangle(x_pos, y_pos, width, height);
    }
}
