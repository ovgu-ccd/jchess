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

    static {
        try {
            image = ImageIO.read(Application.class.getResource("resources/offscreen-map.png"));
        } catch (IOException e) {
            image = null;
        }
    }

    public static BoardCoordinate absoluteCoordinateToBoardCoordinate(int x, int y){
        int[] pixel = image.getData().getPixel(x, y, (int[]) null);
        return new BoardCoordinate(pixel[0], pixel[1], pixel[2]);
    }
}
