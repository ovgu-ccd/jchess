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

import jchess.Application;
import jchess.eventbus.Controller;
import jchess.eventbus.events.*;
import jchess.game.Game;
import jchess.game.board.Board;
import jchess.game.board.InvalidBoardCoordinateException;
import jchess.game.board.Tile;
import jchess.game.pieces.*;
import jchess.util.*;
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
import java.util.HashMap;

/**
 * Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squares of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 *
 * @trace [$REQ31]
 * @trace [$REQ34]
 * @trace [$REQ35]
 */
@Listener(references = References.Strong)
public class BoardView extends JPanel {
    private static BufferedImage boardImage;
    private static BufferedImage possibleMoveImage;

    private BufferedImage piecesOverlay;
    private BufferedImage movesOverlay;
    private BufferedImage statusMessageOverlay;

    private String statusMessage;
    private Timer statusAnimationTimer;

    static {
        try {
            boardImage = ImageIO.read(Application.class.getResource("resources/images/board.png"));
            possibleMoveImage = ImageIO.read(Application.class.getResource("resources/images/tile_highlighter_ramp.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Chessboard class constructor
     */
    public BoardView() {
        Controller.INSTANCE.subscribe(this);

        piecesOverlay = new BufferedImage(boardImage.getWidth(), boardImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        movesOverlay = new BufferedImage(boardImage.getWidth(), boardImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        statusMessageOverlay = new BufferedImage(boardImage.getWidth(), boardImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        this.setDoubleBuffered(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                statusAnimationTimer.stop();
                statusMessageOverlay = new BufferedImage(boardImage.getWidth(), boardImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
                repaint();

                BoardCoordinate boardCoordinate;
                try {
                    boardCoordinate = CoordinateConverter.pixelToBoardCoordinate(e.getX(), e.getY());

                    GameTab gameTab = (GameTab) getParent();
                    SelectEvent selectEvent = new SelectEvent(gameTab.getGame(), boardCoordinate);

                    Logging.GUI.debug("Emit SelectEvent: " + selectEvent.toString());
                    selectEvent.emit();
                } catch (PixelCoordinateNotOnBoardException e1) {
                    GenericErrorEvent genericErrorEvent = new GenericErrorEvent(this, e1);
                    genericErrorEvent.emit();
                }
            }
        });
    }

    private Game getGame() {
        return ((GameTab) getParent()).getGame();
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(65, 40, 0));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.drawImage(boardImage, 0, 0, boardImage.getWidth(), boardImage.getHeight(), null);

        g2d.drawImage(piecesOverlay, null, 0, 0);
        g2d.drawImage(movesOverlay, null, 0, 0);
        g2d.drawImage(statusMessageOverlay, null, 0, 0);
    }


    @Override
    public void update(Graphics g) {
        repaint();
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(boardImage.getWidth(), boardImage.getHeight());
    }


    @Override
    public Dimension getMaximumSize() {
        return new Dimension(boardImage.getWidth(), boardImage.getHeight());
    }

    public BufferedImage getStatusMessageOverlay() {
        return statusMessageOverlay;
    }

    public void setStatusMessageOverlay(BufferedImage statusMessageOverlay) {
        this.statusMessageOverlay = statusMessageOverlay;
    }

    public Timer getStatusAnimationTimer() {
        return statusAnimationTimer;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(boardImage.getWidth(), boardImage.getHeight());
    }


    public int getWidth() {
        return boardImage.getWidth();
    }


    public int getHeight() {
        return boardImage.getHeight();
    }


    void renderPiece(Graphics2D g2d, Piece piece, int a, int b) {

        PixelCoordinate pixelCoordinate =
                CoordinateConverter.boardToPixelCoordinate(a, b);

        if (piece != null) {
            switch (piece.getPlayerID()) {
                case 0:
                    g2d.setColor(Color.green);
                    break;
                case 1:
                    g2d.setColor(new Color(0, 127, 255));
                    break;
                case 2:
                    g2d.setColor(Color.orange);
                    break;
            }
            if (piece instanceof King) {
                g2d.drawString("\u265a", pixelCoordinate.x - 16, pixelCoordinate.y + 7);
            } else if (piece instanceof Queen) {
                g2d.drawString("\u265b", pixelCoordinate.x - 16, pixelCoordinate.y + 7);
            } else if (piece instanceof Rook) {
                g2d.drawString("\u265c", pixelCoordinate.x - 16, pixelCoordinate.y + 7);
            } else if (piece instanceof Bishop) {
                g2d.drawString("\u265d", pixelCoordinate.x - 16, pixelCoordinate.y + 7);
            } else if (piece instanceof Knight) {
                g2d.drawString("\u265e", pixelCoordinate.x - 16, pixelCoordinate.y + 7);
            } else if (piece instanceof Pawn) {
                g2d.drawString("\u265f", pixelCoordinate.x - 16, pixelCoordinate.y + 7);
            }
        }
    }

    @Handler
    void handleUpdateBoardEvent(UpdateBoardEvent updateBoardEvent) {
        if (updateBoardEvent.shouldReceive(getGame())) {
            Logging.GUI.debug("BoardView: Received UpdateBoardEvent");

            movesOverlay = new BufferedImage(boardImage.getWidth(), boardImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            piecesOverlay = new BufferedImage(boardImage.getWidth(), boardImage.getHeight(), BufferedImage.TYPE_INT_ARGB);


            Graphics2D g2d = (Graphics2D) piecesOverlay.getGraphics();

            g2d.setFont(new Font(g2d.getFont().getName(), Font.PLAIN, 30));

            Board board = updateBoardEvent.getBoard();


            for (int a = 0; a < 8; a++) {
                for (int b = 0; b < (8 + a); b++) {
                    int tileIndex = CoordinateConverter.boardCoordinateToIndex(a, b);
                    Tile tile = null;
                    try {
                        tile = board.getTile(tileIndex);
                        renderPiece(g2d, tile.getPiece(), a, b);
                    } catch (InvalidBoardCoordinateException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (int a = 8; a < 15; a++) {
                int g = a - 9;
                for (int b = g + 2; b < 15; ++b) {
                    int tileIndex = CoordinateConverter.boardCoordinateToIndex(a, b);
                    Tile tile = null;
                    try {
                        tile = board.getTile(tileIndex);
                    } catch (InvalidBoardCoordinateException e) {
                        e.printStackTrace();
                    }
                    renderPiece(g2d, tile.getPiece(), a, b);
                }
            }
            repaint();
        }
    }

    @Handler
    void handlePossibleMovesEvent(PossibleMovesEvent possibleMovesEvent) {
        if (possibleMovesEvent.shouldReceive(getGame())) {
            movesOverlay = new BufferedImage(boardImage.getWidth(), boardImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Logging.GUI.debug("BoardView: Received PossibleMovesEvent");
            Graphics2D g2d = (Graphics2D) movesOverlay.getGraphics();
            for (BoardCoordinate boardCoordinate : possibleMovesEvent.getBoardCoordinates()) {
                PixelCoordinate absoluteCoordinate = CoordinateConverter.boardToPixelCoordinate(boardCoordinate);
                int x = absoluteCoordinate.x - (possibleMoveImage.getWidth() / 2) - 1;
                int y = absoluteCoordinate.y - (possibleMoveImage.getHeight() / 2) - 1;
                g2d.drawImage(possibleMoveImage, x, y, possibleMoveImage.getWidth() + 1, possibleMoveImage.getHeight() + 1, null);
            }

            repaint();
        }
    }


    @Handler
    void handleUpdateStatusMessageEvent(final UpdateStatusMessageEvent updateStatusMessageEvent) {
        if (updateStatusMessageEvent.shouldReceive(getGame())) {
            Logging.GUI.debug("BoardView: Received UpdateStatusMessageEvent: ");
            this.statusMessage = updateStatusMessageEvent.getStatusMessage();
            statusAnimationTimer = new Timer(100, new StatusAnimator(this, updateStatusMessageEvent));
            statusAnimationTimer.start();
        }
    }

    @Handler
    void handlePossiblePromotionsEvent(PossiblePromotionsEvent possiblePromotionsEvent) {
        if (possiblePromotionsEvent.shouldReceive(getGame())) {
            Logging.GUI.debug("BoardView: Received PossiblePromotionsEvent");

            try {
                HashMap<String, Class<? extends Piece>> possibilities = new HashMap<>();
                for (Class<? extends Piece> piece : possiblePromotionsEvent.getPossiblePromotions()) {
                    possibilities.put(piece.getSimpleName(), piece);
                }
                Class<? extends Piece> piece = possibilities.get((String) JOptionPane
                        .showInputDialog(this, "Chose one of the following promotions", "Select a Promotion",
                                JOptionPane.PLAIN_MESSAGE, null, possibilities.keySet().toArray(),
                                possibilities.keySet().toArray()[0]));
                PromotionSelectEvent promotionSelectEvent = new PromotionSelectEvent(getGame(), piece);
                Logging.GUI.debug("BoardView: Emit PromotionSelectEvent");
                promotionSelectEvent.emit();
            } catch (RuntimeException e) {
                e.printStackTrace();
                (new GenericErrorEvent(this, e)).emit();
            }
        }
    }

}
