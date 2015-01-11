package jchess.util;

import jchess.Application;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Static methods allowing easy conversion between (pseudo-polar) BoardCoordinates and (carthesian) AbsoluteCoordinates.
 */
public class CoordinateConverter {
    private static BufferedImage image;
    private static double du;
    private static double[][] directions;
    private static double[][] startDirections;
    private static double M;

    static {
        M = Math.tan(Math.toRadians(150));
        directions = new double[][]{{1, M}, {0, -1}, {-1, M}, {-1, -M}, {0, 1}, {1, -M}};
        startDirections = new double[][]{{0, 1}, {1, -M}, {1, M}, {0, -1}, {-1, M}, {-1, -M}};
        for (int i = 0; i < directions.length; i++) {
            normalize(directions[i]);
            normalize(startDirections[i]);
        }
        try {
            image = ImageIO.read(Application.class.getResource("images.org/TilePicker.png"));
            du = image.getHeight() / 15;
        } catch (IOException e) {
            image = null;
        }
    }

    public static BoardCoordinate absoluteCoordinateToBoardCoordinate(AbsoluteCoordinate ac) throws AbsoluteCoordinateNotOnBoardException {
        int[] pixel = image.getData().getPixel(ac.x, ac.y, (int[]) null);
        if (pixel[0] == 255 && pixel[1] == 255 && pixel[2] == 255) {
            throw new AbsoluteCoordinateNotOnBoardException();
        }
        return new BoardCoordinate(pixel[0], pixel[1], pixel[2]);
    }

    public static BoardCoordinate absoluteCoordinateToBoardCoordinate(int x, int y) throws AbsoluteCoordinateNotOnBoardException {
        AbsoluteCoordinate ac = new AbsoluteCoordinate(x, y);
        return absoluteCoordinateToBoardCoordinate(ac);
    }

    public static AbsoluteCoordinate boardCoordinateToAbsoluteCoordinate(BoardCoordinate bc) {
        int x = 0, y = 0;

        if (bc.getRing() > 0) {

            double startX = startDirections[bc.getPos() / bc.getRing()][0] * bc.getRing() * du;
            double startY = startDirections[bc.getPos() / bc.getRing()][1] * bc.getRing() * du;

            double[] edgeDir = directions[bc.getPos() / bc.getRing()];
            int inEdgeIndex = bc.getPos() % bc.getRing();

            x = (int) Math.round(startX + edgeDir[0] * inEdgeIndex * du);
            y = (int) Math.round(startY + edgeDir[1] * inEdgeIndex * du);
        }

        x += image.getWidth() / 2;
        y = image.getHeight() / 2 - y;

        return new AbsoluteCoordinate(x, y);
    }

    public static AbsoluteCoordinate boardCoordinateToAbsoluteCoordinate(int ring, int pos, int abs) {
        return boardCoordinateToAbsoluteCoordinate(new BoardCoordinate(ring, pos, abs));
    }

    private static void normalize(double[] vec) {
        double len = Math.sqrt(vec[0] * vec[0] + vec[1] * vec[1]);
        vec[0] /= len;
        vec[1] /= len;
    }
}
