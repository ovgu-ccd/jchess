package jchess.util;

/**
 * Pseudo-Polar Board Coordinates
 */
public class BoardCoordinate {
    int ring, pos, abs;

    public BoardCoordinate(int ring, int pos, int abs) {
        this.ring = ring;
        this.pos = pos;
        this.abs = abs;
    }

    public String toString() {
        return "(BC) Ring: " + ring + " Position: " + pos + " Abs: " + abs;
    }
}
