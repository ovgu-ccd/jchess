package jchess.util;

/**
 * Tweaked hexagonal grid axis coordinates for the board
 * TODO: Add Contracts that some pairs of a and b are not reachable, e.g. a=9, b=0 or a=10, b=1
 */
public class BoardCoordinate {
    private final int a;
    private final int b;
    private final int i;

    public BoardCoordinate(int a, int b) {
        this.a = a;
        this.b = b;
        this.i = CoordinateConverter.boardCoordinateToIndex(a, b);
    }

    public String toString() {
        return "(BC) A: " + a + " B: " + b + " Abs: " + i;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return b - a;
    }

    public int getI() {
        return i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardCoordinate that = (BoardCoordinate) o;

        if (a != that.a) return false;
        if (b != that.b) return false;
        //noinspection RedundantIfStatement
        if (i != that.i) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = a;
        result = 31 * result + b;
        result = 31 * result + i;
        return result;
    }
}
