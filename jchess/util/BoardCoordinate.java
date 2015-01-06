package jchess.util;

/**
 * Created by robert on 05/01/15.
 */
public class BoardCoordinate {
    int ring, pos;

    public BoardCoordinate(int ring, int pos) {
        this.ring = ring;
        this.pos = pos;
    }

    public String toString() {
        return "(BC) Ring: " + ring + " Position: " + pos;
    }
}
