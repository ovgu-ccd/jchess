package jchess.util;

/**
 * Created by robert on 05/01/15.
 */
public class BoardCoordinate {
    int ring, pos, abs;

    public BoardCoordinate(int ring, int pos, int abs) {
        this.ring = ring;
        this.pos = pos;
    }

    public String toString() {
        return "(BC) Ring: " + ring + " Position: " + pos + " Abs: " + abs;
    }
}
