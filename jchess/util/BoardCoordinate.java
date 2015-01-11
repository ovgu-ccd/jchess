package jchess.util;

/**
 * Pseudo-Polar Board Coordinates
 */
public class BoardCoordinate {
    private int ring;
    private int pos;
    private int abs;

    public BoardCoordinate(int ring, int pos, int abs) {
        this.ring = ring;
        this.pos = pos;
        this.abs = abs;
    }

    public String toString() {
        return "(BC) Ring: " + getRing() + " Position: " + getPos() + " Abs: " + getAbs();
    }

    public int getRing() {
        return ring;
    }

    public int getPos() {
        return pos;
    }

    public int getAbs() {
        return abs;
    }
}
