package jchess.util;


import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateConverterTest {

    @Test
    public void testAbsoluteCoordinateToBoardCoordinate() {
        assertEquals(CoordinateConverter.absoluteCoordinateToBoardCoordinate(225, 256).ring, 0);
        assertEquals(CoordinateConverter.absoluteCoordinateToBoardCoordinate(225, 256).pos, 0);
    }


    @Test
    public void testBoardCoordinateToAbsoluteCoordinate() throws Exception {
        for (int ring = 1; ring < 7; ring++) {
            for (int pos = 0; pos < 6 * ring; pos++) {
                AbsoluteCoordinate ac = CoordinateConverter.boardCoordinateToAbsoluteCoordinate(ring, pos);
                BoardCoordinate bc = CoordinateConverter.absoluteCoordinateToBoardCoordinate(ac);
                assertEquals(bc.ring, ring);
                assertEquals(bc.pos, pos);
            }
        }
    }
}