package jchess.game.board;

import jchess.util.BoardCoordinate;
import jchess.util.CoordinateConverter;

/**
 * Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squares of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 *
 * @trace [$REQ12]
 */
public abstract class Board {
    private Tile[] tiles;

    public Board() {
        initTiles();
        try {
            initFigures();
        } catch (InvalidBoardCoordinateException e) {
            e.printStackTrace();
        }
    }

    void initTiles() {
        //noinspection PointlessArithmeticExpression
        tiles = new Tile[1 + 1 * 6 + 2 * 6 + 3 * 6 + 4 * 6 + 5 * 6 + 6 * 6 + 7 * 6];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile();
        }

    }

    protected abstract void initFigures() throws InvalidBoardCoordinateException;

    /**
     * Access a Tile of a board by absolute index
     * Tile index starts in the board center
     * next tile is above and following tiles are on the same ring
     * next tile is above and so forth ...
     *
     * @param tileIndex Index for the ring, starting at 0 with the center-Top Tile
     *                  Parameter must be between [0..168] otherwise Tile is not on Board and null is returned
     */
    public Tile getTile(int tileIndex) throws InvalidBoardCoordinateException {
        if (tileIndex < 0 || tileIndex > 168)
            throw new InvalidBoardCoordinateException();
        return tiles[tileIndex];
    }

    /**
     * Access a Tile of a board by axial coordinates as explained in the following article:
     * http://www.redblobgames.com/grids/hexagons/
     * the Tiles are ordered differently due to inner workings of the picking system
     * Tile( 0, 0 ) is the top most tile
     * Tile( 1, 0 ) is left bellow
     * Tile( 0, 1 ) is right bellow the top most tile and so forth
     * the third cubical coordinate can be computed with b - a, while for this one negative values are valid
     * All the Tiles on the center column have the cubical coordinate c = 0
     * the columns to the left are negative, and to the right are positive
     *
     * @param a first axial coordinate
     * @param b second axial coordinate
     */
    public Tile getTile(int a, int b) throws InvalidBoardCoordinateException {
        return getTile(CoordinateConverter.boardCoordinateToIndex(a, b));
    }

    /**
     * Access a Tile of a board by a BoardCoordinate Class
     *
     * @param boardCoordinate well ...
     */
    public Tile getTile(BoardCoordinate boardCoordinate) throws InvalidBoardCoordinateException {
        return getTile(boardCoordinate.getI());
    }

    /**
     * Get number of tiles on board.
     */
    public int getTileCount() {
        return tiles.length;
    }
}

