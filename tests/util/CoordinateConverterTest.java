package tests.util;


import jchess.util.BoardCoordinate;
import jchess.util.CoordinateConverter;
import jchess.util.PixelCoordinate;
import jchess.util.PixelCoordinateNotOnBoardException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoordinateConverterTest {

    @Test
    // Some of these coordinates do not exit on board, but here only the conversion functions are tested
    // Failing Coordinates are removed with if clause
    public void testBoardCoordinateToPixelCoordinate() throws PixelCoordinateNotOnBoardException {
        for (int a = 1; a < 15; ++a) {
            for (int b = 0; b < 15; ++b) {
                if ( a >= 0 &&
                     a < 15 &&
                     b >= 0 &&
                     b < 15 &&
                     (b-a) >= -7 &&
                     (b-a) <= 7) {
                    int index = CoordinateConverter.boardCoordinateToIndex(a, b);
                    PixelCoordinate pc = CoordinateConverter.boardToPixelCoordinate(a, b);
                    BoardCoordinate bc = CoordinateConverter.pixelToBoardCoordinate(pc);
                    assertEquals(a, bc.getA());
                    assertEquals(b, bc.getB());
                    assertEquals(b - a, bc.getC());
                    assertEquals(index, bc.getI());
                }
            }
        }
    }

    @Test( expected = PixelCoordinateNotOnBoardException.class)
    // This test also throws an exception if a boardCoordinate is not on board
    public void testPixelCoordinateNotOnBoard() throws PixelCoordinateNotOnBoardException {
        for (int a = 1; a < 15; ++a) {
            for (int b = 0; b < 15; ++b) {
                if ( !( a >= 0 &&
                        a < 15 &&
                        b >= 0 &&
                        b < 15 &&
                        (b-a) >= -7 &&
                        (b-a) <= 7)) {
                    PixelCoordinate pc = CoordinateConverter.boardToPixelCoordinate(a, b);
                    CoordinateConverter.pixelToBoardCoordinate(pc);
                }
            }
        }
    }
}