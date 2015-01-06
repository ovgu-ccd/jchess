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
            image = ImageIO.read(Application.class.getResource("resources/offscreen-map.png"));
            du = image.getHeight() / 15;
        } catch (IOException e) {
            image = null;
        }
    }

    public static BoardCoordinate absoluteCoordinateToBoardCoordinate(AbsoluteCoordinate ac) {
        int[] pixel = image.getData().getPixel(ac.x, ac.y, (int[]) null);
        return new BoardCoordinate(pixel[0], pixel[1]);
    }

    public static BoardCoordinate absoluteCoordinateToBoardCoordinate(int x, int y) {
        AbsoluteCoordinate ac = new AbsoluteCoordinate(x, y);
        return absoluteCoordinateToBoardCoordinate(ac);
    }

    public static AbsoluteCoordinate boardCoordinateToAbsoluteCoordinate(BoardCoordinate bc) {
        int x = 0, y = 0;

        if (bc.ring > 0) {

            double startX = startDirections[bc.pos / bc.ring][0] * bc.ring * du;
            double startY = startDirections[bc.pos / bc.ring][1] * bc.ring * du;

            double[] edgeDir = directions[bc.pos / bc.ring];
            int inEdgeIndex = bc.pos % bc.ring;

            x = (int) Math.round(startX + edgeDir[0] * inEdgeIndex * du);
            y = (int) Math.round(startY + edgeDir[1] * inEdgeIndex * du);
        }

        x += image.getWidth() / 2;
        y = image.getHeight() / 2 - y;

        return new AbsoluteCoordinate(x, y);
    }

    public static AbsoluteCoordinate boardCoordinateToAbsoluteCoordinate(int ring, int pos) {
        return boardCoordinateToAbsoluteCoordinate(new BoardCoordinate(ring, pos));
    }

    private static void normalize(double[] vec) {
        double len = Math.sqrt(vec[0] * vec[0] + vec[1] * vec[1]);
        vec[0] /= len;
        vec[1] /= len;
    }
}
