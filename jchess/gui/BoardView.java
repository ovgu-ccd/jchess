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
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package jchess.gui;

import jchess.*;
import jchess.util.AbsoluteCoordinate;
import jchess.util.AbsoluteCoordinateNotOnBoardException;
import jchess.util.BoardCoordinate;
import jchess.util.CoordinateConverter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squers of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 */
public class BoardView extends JPanel {
    private static BufferedImage boardImage;

    private static final Image org_sel_square = GUIUtils
            .loadImage("sel_square.png");//image of highlited square
    private static final Image org_able_square = GUIUtils
            .loadImage("able_square.png");//image of square where piece can go

    private ArrayList moves;
    private boolean fontSet = false;
    private BufferedImage offscreen;

    /**
     * Chessboard class constructor
     *
     * @param settings reference to Settings class object for this chessboard
     */
    public BoardView(Settings settings) {
        try {
            boardImage = ImageIO.read(Application.class.getResource("images.org/Board.png"));
            offscreen = new BufferedImage(boardImage.getWidth(), boardImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setDoubleBuffered(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BoardCoordinate bc = null;
                try {
                    bc = CoordinateConverter.absoluteCoordinateToBoardCoordinate(e.getX(), e.getY());
                    AbsoluteCoordinate ac = CoordinateConverter.boardCoordinateToAbsoluteCoordinate(bc);
                    x = ac.x;
                    y = ac.y;
                    repaint();
                } catch (AbsoluteCoordinateNotOnBoardException e1) {
                    // Send no event
                }
            }
        });
    }/*--endOf-Chessboard--*/

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(boardImage.getWidth(), boardImage.getHeight());
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(boardImage.getWidth(), boardImage.getHeight());
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(boardImage.getWidth(), boardImage.getHeight());
    }


    int x, y;

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(offscreen, null, 0, 0);

        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.drawImage(boardImage, 0, 0, boardImage.getWidth(), boardImage.getHeight(), null);//draw an Image of chessboard

        g2d.setColor(Color.gray);

        if (!fontSet) {
            g2d.setFont(new Font(g2d.getFont().getName(), Font.PLAIN, 30));
        }

        g2d.drawString("\u265e", x - 13, y + 10);
    }

    public int getWidth() {
        return boardImage.getWidth();
    }

    public int getHeight() {
        return boardImage.getHeight();
    }

    /**
     * Annotations to superclass Game updateing and painting the crossboard
     */
    @Override
    public void update(Graphics g) {
        repaint();
    }


}
