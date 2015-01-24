package jchess.tests;


import jchess.util.BoardCoordinate;
import jchess.util.CoordinateConverter;
import jchess.util.PixelCoordinate;
import jchess.util.PixelCoordinateNotOnBoardException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoordinateConverterTest {

    /*@Test
    public void testBoardCoordinateToAbsoluteCoordinate() throws Exception, PixelCoordinateNotOnBoardException {
        int abs = 1;
        for (int ring = 1; ring < 8; ring++) {
            for (int pos = 0; pos < 6 * ring; pos++) {
                PixelCoordinate ac = CoordinateConverter.boardToPixelCoordinate(ring, pos, abs + pos);
                BoardCoordinate bc = CoordinateConverter.pixelToBoardCoordinate(ac);
                assertEquals(ring, bc.getA());
                assertEquals(pos, bc.getB());
                assertEquals(abs + pos, bc.getI());
            }
            abs += ring * 6;
        }
    }*/
}