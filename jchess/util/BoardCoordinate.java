package jchess.util;

/**
 * Pseudo-Polar Board Coordinates
 */
public class BoardCoordinate {
    int a, b, c, i;

    public BoardCoordinate(int a, int b, int i) {
        this.a = a;
        this.b = b;
        this.c = b - a;
        this.i = i;
    }

    public String toString() {
        return "(BC) Ring: " + a + " Position: " + b + " Abs: " + i;
    }
}
