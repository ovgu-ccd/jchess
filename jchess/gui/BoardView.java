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
import jchess.mvc.Controller;
import jchess.mvc.events.PossibleMovesEvent;
import jchess.mvc.events.SelectEvent;
import jchess.mvc.events.UpdateBoardEvent;
import jchess.pieces.*;
import jchess.util.BoardCoordinate;
import jchess.util.CoordinateConverter;
import jchess.util.PixelCoordinate;
import jchess.util.PixelCoordinateNotOnBoardException;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squers of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 */
@Listener(references = References.Strong)
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
        Controller.INSTANCE.subscribe(this);
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
                //try {
                    int[] p = CoordinateConverter.getPixel(new PixelCoordinate(e.getX(), e.getY()));
                    Logging.GUI.debug("A: " + p[0] + " B: " + p[1] + " C: " + (p[1] - p[0]) +
                            " I: " + CoordinateConverter.boardAxialCoordinateToIndex( p[0] , p[1] ));

                    GameTab gameTab = (GameTab) getParent();
                    SelectEvent selectEvent = new SelectEvent(gameTab.getGame(), bc);


                    Logging.GUI.debug("Emit SelectEvent");
                    selectEvent.emit();

                //} catch (PixelCoordinateNotOnBoardException e1) {

                    // Send no event
                //}
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


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        g2d.drawImage(offscreen, null, 0, 0);
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

    @Handler
    void handleUpdateBoardEvent(UpdateBoardEvent updateBoardEvent) {
        if (updateBoardEvent.shouldReceive(getGame())) {
            Logging.GUI.debug("BoardView: Received UpdateBoardEvent");

            Graphics2D g2d = (Graphics2D) offscreen.getGraphics();
            g2d.setColor(Color.GRAY);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.drawImage(boardImage, 0, 0, boardImage.getWidth(), boardImage.getHeight(), null);

            if (!fontSet) {
                g2d.setFont(new Font(g2d.getFont().getName(), Font.PLAIN, 30));
            }

            Board board = updateBoardEvent.getBoard();


            if (board.getTile(0).getPiece() != null) {
                PixelCoordinate pc =
                        CoordinateConverter.boardToPixelCoordinate(0, 0, 0);
                g2d.drawString("\u2658", pc.x - 15, pc.y + 20);
            }

            int sum = 1;
            int abs;
            for (int ring = 1; ring < 8; ring++) {
                for (int pos = 0; pos < 6 * ring; pos++) {
                    abs = sum + pos;
                    PixelCoordinate pc =
                            CoordinateConverter.boardToPixelCoordinate(ring, pos, abs);
                    //g2d.drawString(""+abs, pc.x - 16, pc.y + 7);
                    Piece piece = board.getTile(abs).getPiece();
                    if (piece != null) {
                        switch (piece.getPlayerID()) {
                            case 0:
                                g2d.setColor(Color.green);
                                break;
                            case 1:
                                g2d.setColor(Color.blue);
                                break;
                            case 2:
                                g2d.setColor(Color.orange);
                                break;
                        }
                        if (piece instanceof King) {
                            g2d.drawString("\u265a", pc.x - 16, pc.y + 7);
                        } else if (piece instanceof Queen) {
                            g2d.drawString("\u265b", pc.x - 16, pc.y + 7);
                        } else if (piece instanceof Rook) {
                            g2d.drawString("\u265c", pc.x - 16, pc.y + 7);
                        } else if (piece instanceof Bishop) {
                            g2d.drawString("\u265d", pc.x - 16, pc.y + 7);
                        } else if (piece instanceof Knight) {
                            g2d.drawString("\u265e", pc.x - 16, pc.y + 7);
                        } else if (piece instanceof Pawn) {
                            g2d.drawString("\u265f", pc.x - 16, pc.y + 7);
                        }
                    }
                }
                sum += ring * 6;
            }

            repaint();
        }
    }

    @Handler
    void handlePossibleMovesEvent(PossibleMovesEvent possibleMovesEvent) {
        if (possibleMovesEvent.shouldReceive(getGame())) {
            Logging.GUI.debug("BoardView: Received PossibleMovesEvent");
            // TODO
        }
    }

    private Game getGame() {
        return ((GameTab) getParent()).getGame();
    }
}
