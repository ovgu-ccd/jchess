package jchess.gui;

import jchess.Application;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by robert on 05/01/15.
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
        for (int i = 0; i<directions.length; i++){
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
        return absoluteCoordinateToBoardCoordinate(new AbsoluteCoordinate(x, y));
    }

    public static AbsoluteCoordinate boardCoordinateToAbsoluteCoordinate(BoardCoordinate bc) {
        double startX = startDirections[bc.pos / bc.ring][0] * bc.ring * du;
        double startY = startDirections[bc.pos / bc.ring][1] * bc.ring * du;

        double[] edgeDir = directions[bc.pos / bc.ring];
        int inEdgeIndex = bc.pos % bc.ring;

        int x = (int) Math.round(startX + edgeDir[0] * inEdgeIndex * du);
        int y = (int) Math.round(startY + edgeDir[1] * inEdgeIndex * du);

        return new AbsoluteCoordinate(x, y);
    }

    public static AbsoluteCoordinate boardCoordinateToAbsoluteCoordinate(int ring, int pos) {
        return boardCoordinateToAbsoluteCoordinate(new BoardCoordinate(ring, pos));
    }

    private static void normalize(double[] vec){
        double len = Math.sqrt(vec[0]*vec[0] + vec[1] * vec[1]);
        vec[0] /= len;
        vec[1] /= len;
     }
}
