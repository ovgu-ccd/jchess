package jchess.util;

import jchess.Application;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Static methods allowing easy conversion between (hexagonal grid axial) BoardCoordinates and (cartesian) PixelCoordinates.
 * TODO: define height_1_2 and width_3_4 based on imaged dimensions
 */
public class CoordinateConverter {
    private static BufferedImage image;

    static {
        try {
            image = ImageIO.read(Application.class.getResource("images.org/TilePicker.png"));
            //hexHight = image.getHeight() / 15;
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
        double width_3_4 = 72.0 / Math.sqrt( 3.0 );
        int height_1_2 = 24;

        int x = 320 + ( int )Math.round( ( bc.b - bc.a ) * width_3_4 ) ;
        int y = 24 + height_1_2 * ( bc.a + bc.b );

        return new PixelCoordinate(x, y);
    }

    public static PixelCoordinate boardToPixelCoordinate(int a, int b, int i) {
        return boardToPixelCoordinate(new BoardCoordinate(a, b, i));
    }

    /// TODO: Works correct but should have an official test and contracts, same as BoardCoordinates class
    public static int boardAxialCoordinateToIndex( int a, int b ) {

        if ( a < 8 ) {
            return 7 * a + b + /*GAUSS*/ a * (a + 1) / 2;
        }

        else {
            int g = a - 9;
            return 104 + 13 * g + b - /*GAUSS*/ g * ( g + 1 ) / 2 ;
        }
    }
}
