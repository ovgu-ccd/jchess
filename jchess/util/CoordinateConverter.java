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


    public static BoardCoordinate pixelToBoardCoordinate(PixelCoordinate ac) throws PixelCoordinateNotOnBoardException {
        int[] pixel = getPixel(ac);
        if (pixel[0] == 255 && pixel[1] == 255 && pixel[2] == 255) {
            throw new PixelCoordinateNotOnBoardException();
        }
        return new BoardCoordinate(pixel[0], pixel[1], pixel[2]);
    }

    public static int[] getPixel(PixelCoordinate ac) {
        return image.getData().getPixel(ac.x, ac.y, (int[]) null);
    }

    public static BoardCoordinate pixelToBoardCoordinate(int x, int y) throws PixelCoordinateNotOnBoardException {
        PixelCoordinate ac = new PixelCoordinate(x, y);
        return pixelToBoardCoordinate(ac);
    }

    public static PixelCoordinate boardToPixelCoordinate(BoardCoordinate bc) {

        //double width = 96.0 / Math.sqrt( 3.0 );
        double width_3_4 = 72.0 / Math.sqrt( 3.0 );
        int height_1_2 = 24;

        //int x = ( int )( Math.round((bc.c + 7) * 0.75 * width + 0.5 * width ));
        int x = 320 + ( int )Math.round( ( bc.b - bc.a ) * width_3_4 ) ;
        int y = 24 + height_1_2 * ( bc.a + bc.b );

        return new PixelCoordinate(x, y);
    }

    public static PixelCoordinate boardToPixelCoordinate(int a, int b, int i) {
        return boardToPixelCoordinate(new BoardCoordinate(a, b, i));
    }

    private static void normalize(double[] vec) {
        double len = Math.sqrt(vec[0] * vec[0] + vec[1] * vec[1]);
        vec[0] /= len;
        vec[1] /= len;
    }
}
