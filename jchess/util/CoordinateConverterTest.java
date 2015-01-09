package jchess.util;


import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateConverterTest {

    @Test
    public void testBoardCoordinateToAbsoluteCoordinate() throws Exception, AbsoluteCoordinateNotOnBoardException {
        int abs = 0;
        for (int ring = 1; ring < 7; ring++) {
            abs += ring * 6;
            for (int pos = 0; pos < 6 * ring; pos++) {
                AbsoluteCoordinate ac = CoordinateConverter.boardCoordinateToAbsoluteCoordinate(ring, pos, abs + pos);
                BoardCoordinate bc = CoordinateConverter.absoluteCoordinateToBoardCoordinate(ac);
                assertEquals(bc.ring, ring);
                assertEquals(bc.pos, pos);
                //assertEquals(bc.abs, abs + pos);
            }
        }
    }
}