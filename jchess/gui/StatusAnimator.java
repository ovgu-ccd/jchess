package jchess.gui;

import jchess.Application;
import jchess.eventbus.events.UpdateStatusMessageEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Shows status notifications to the user
 * <p/>
 * Created by robert on 20.01.15.
 *
 * @trace [$REQ36]
 * @trace [$REQ37]
 */
class StatusAnimator implements ActionListener {
    private static final float FONTSIZE = 30;
    private static Font font;

    static {
        InputStream is = Application.class.getResourceAsStream("resources/roboto-font/RobotoCondensed-Light.ttf");
        try {
            Font unsizedFont = Font.createFont(Font.TRUETYPE_FONT, is);
            font = unsizedFont.deriveFont(35f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    private final int animationTime = 2000;

    private final UpdateStatusMessageEvent updateStatusMessageEvent;
    private final BoardView parent;
    private final long dueTime;

    public StatusAnimator(BoardView parent, UpdateStatusMessageEvent updateStatusMessageEvent) {
        this.parent = parent;
        this.updateStatusMessageEvent = updateStatusMessageEvent;
        dueTime = System.currentTimeMillis() + animationTime;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        long now = System.currentTimeMillis();
        parent.setStatusMessageOverlay(new BufferedImage(parent.getWidth(), parent.getHeight(), BufferedImage.TYPE_INT_ARGB));
        if (now > dueTime) {
            parent.getStatusAnimationTimer().stop();
        } else {
            Graphics2D g2d = (Graphics2D) parent.getStatusMessageOverlay().getGraphics();
            setColor(now, g2d);
            drawBackground(g2d);
            drawMessage(g2d);
        }
        parent.repaint();
    }


    private void setColor(long now, Graphics2D g2d) {
        // Set base color
        int r, g, b;
        switch (updateStatusMessageEvent.getTypes()) {
            case NORMAL:
                r = 153;
                g = 255;
                b = 51;
                break;
            case ALERT:
                r = 255;
                g = 48;
                b = 0;
                break;
            default:
                r = 220;
                g = 220;
                b = 220;
                break;
        }


        // Set opacity
        double alpha = dueTime - now;
        alpha = (513.0 / 2000) * alpha - (129.0 / 2000000) * alpha * alpha;
        Color c = new Color(r, g, b, (int) alpha);
        g2d.setColor(c);
    }

    private void drawBackground(Graphics2D g2d) {
        // Draw background
        int w = 500, h = 80;
        int x = parent.getWidth() / 2 - w / 2, y = parent.getHeight() / 2 - h / 2;
        g2d.fillRoundRect(x, y, w, h, 10, 10);
    }

    private void drawMessage(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        // Draw text
        if (font == null) {
            font = g2d.getFont().deriveFont(FONTSIZE);
        }
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics(font);
        Rectangle2D stringBounds = metrics.getStringBounds(parent.getStatusMessage(), g2d);
        int x = (int) (parent.getWidth() / 2 - stringBounds.getWidth() / 2);
        int y = (int) (parent.getHeight() / 2 + stringBounds.getHeight() / 2) - 5;
        g2d.drawString(parent.getStatusMessage(), x, y);
    }

}
