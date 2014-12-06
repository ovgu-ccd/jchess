package jchess.guirewrite;

import jchess.GUIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Created by robert on 06.12.14.
 */
public class BoardView extends JPanel implements MouseListener {
    private static BufferedImage boardImage = GUIUtils.loadImage("chessboard.png");

    public BoardView() {
        setPreferredSize(new Dimension(boardImage.getWidth(), boardImage.getHeight()));
        addMouseListener(this);
    }



    public void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
        x = x - (r / 2);
        y = y - (r / 2);
        g.drawOval(x, y, r, r);
    }

    public void paintComponent(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;

        g2d.drawImage(boardImage, 0, 0, null);

        for (int i = 1; i < 8; i++) {
            drawCenteredCircle(g2d, boardImage.getWidth()/2, boardImage.getHeight()/2, 2* i * boardImage.getHeight()/15);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.add(new BoardView());
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
