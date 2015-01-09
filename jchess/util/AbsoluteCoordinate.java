package jchess.util;

/**
 * Cartesian Absolute Coordinates
 */
public class AbsoluteCoordinate {
    public int x, y;

    public AbsoluteCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(AC) X: " + x + " Y:" + y;
    }
}
