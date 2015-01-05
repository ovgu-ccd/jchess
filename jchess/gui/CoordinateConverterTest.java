package jchess.gui;

import junit.framework.TestCase;

public class CoordinateConverterTest extends TestCase {

    public void testAbsoluteCoordinateToBoardCoordinate() {
        assertEquals(CoordinateConverter.absoluteCoordinateToBoardCoordinate(225, 256).ring, 0);
        assertEquals(CoordinateConverter.absoluteCoordinateToBoardCoordinate(225, 256).pos, 0);
        assertEquals(CoordinateConverter.absoluteCoordinateToBoardCoordinate(225, 256).abs, 0);
    }
}