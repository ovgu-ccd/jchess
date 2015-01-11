package jchess.util;


import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateConverterTest {

    @Test
    public void testBoardCoordinateToAbsoluteCoordinate() throws Exception, PixelCoordinateNotOnBoardException {
        int abs = 1;
        for (int ring = 1; ring < 7; ring++) {
            for (int pos = 0; pos < 6 * ring; pos++) {
                PixelCoordinate ac = CoordinateConverter.boardToPixelCoordinate(ring, pos, abs + pos);
                BoardCoordinate bc = CoordinateConverter.pixelToBoardCoordinate(ac);
                assertEquals(ring, bc.a);
                assertEquals(pos, bc.b);
                assertEquals(abs + pos, bc.i);
            }
            abs += ring * 6;
        }
    }
}