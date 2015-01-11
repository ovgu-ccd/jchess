package jchess.util;

/**
 * Tweaked hexagonal grid axis coordinates for the board
 * TODO: Add Contracts that some pairs of a and b are not reachable, e.g. a=9, b=0 or a=10, b=1
 */
public class BoardCoordinate {
    int a, b, i;

    public BoardCoordinate(int a, int b, int i) {
        this.a = a;
        this.b = b;
        this.i = i;
    }

    public String toString() {
        return "(BC) Ring: " + a + " Position: " + b + " Abs: " + i;
    }
}
