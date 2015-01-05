package jchess.gui;

/**
 * Created by robert on 05/01/15.
 */
public class BoardCoordinate {
    int ring, pos, abs;

    public BoardCoordinate(int ring, int pos, int abs) {
        this.ring = ring;
        this.pos = pos;
        this.abs = abs;
    }

    public String toString() {
        return "Ring: " + ring + " Position: " + pos + " (" + abs + ")";
    }
}
