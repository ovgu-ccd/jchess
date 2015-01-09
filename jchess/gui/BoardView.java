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

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squers of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 */
public class BoardView extends JPanel {
    private static final BufferedImage boardImage = GUIUtils.loadImage("chessboard2.png");//image of chessboard
    private static final Image org_sel_square = GUIUtils
            .loadImage("sel_square.png");//image of highlited square
    private static final Image org_able_square = GUIUtils
            .loadImage("able_square.png");//image of square where piece can go
    private static Image image = BoardView.boardImage;//image of chessboard

    private ArrayList moves;




    /**
     * Chessboard class constructor
     *
     * @param settings      reference to Settings class object for this chessboard
     */
    public BoardView(Settings settings) {
        this.setDoubleBuffered(true);
        setPreferredSize(new Dimension(boardImage.getWidth(), boardImage.getHeight()));

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


    int x, y;

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(image, 0, 0, boardImage.getWidth(), boardImage.getHeight(), null);//draw an Image of chessboard

        g2d.setColor(Color.red);
        g2d.fillRect(x - 2, y - 2, 4, 4);
    }

    public int getWidth(){
        return boardImage.getWidth();
    }

    public int getHeight(){
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
