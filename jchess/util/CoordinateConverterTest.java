package jchess.util;


import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateConverterTest {

    @Test
    public void testBoardCoordinateToAbsoluteCoordinate() throws Exception, AbsoluteCoordinateNotOnBoardException {
        int abs = 1;
        for (int ring = 1; ring < 8; ring++) {
            for (int pos = 0; pos < 6 * ring; pos++) {
                AbsoluteCoordinate ac = CoordinateConverter.boardCoordinateToAbsoluteCoordinate(ring, pos, abs + pos);
                BoardCoordinate bc = CoordinateConverter.absoluteCoordinateToBoardCoordinate(ac);
                assertEquals(ring, bc.getRing());
                assertEquals(pos, bc.getPos());
                assertEquals(abs + pos, bc.getAbs());
            }
            abs += ring * 6;
        }
    }
}