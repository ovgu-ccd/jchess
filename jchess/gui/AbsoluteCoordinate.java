package jchess.gui;

/**
 * Created by robert on 06/01/15.
 */
public class AbsoluteCoordinate {
    int x, y;

    public AbsoluteCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(AC) X: " + x + " Y:" + y;
    }
}
