package jchess.util;

/**
 * Cartesian Absolute Coordinates
 */
public class PixelCoordinate {
    public int x, y;

    public PixelCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(AC) X: " + x + " Y:" + y;
    }
}
