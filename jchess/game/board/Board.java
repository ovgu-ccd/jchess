package jchess.game.board;

import jchess.util.BoardCoordinate;
import jchess.util.CoordinateConverter;

/**
 * Created by robert on 24/01/15.
 */
public abstract  class Board {
    protected Tile[] tiles;

    public Board(){
        initTiles();
        initFigures();
    }

    public void initTiles() {
        tiles = new Tile[1 + 1 * 6 + 2 * 6 + 3 * 6 + 4 * 6 + 5 * 6 + 6 * 6 + 7 * 6];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile();
        }
    }

    protected abstract void initFigures();

    /**
     * Access a Tile of a board by absolute index
     * Tile index starts in the board center
     * next tile is above and following tiles are on the same ring
     * next tile is above and so forth ...
     *
     * @param tileIndex Index for the ring, starting at 0 with the center tile/ring
     */
    public Tile getTile(int tileIndex) {
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
    public Tile getTile(int a, int b) {
        return tiles[CoordinateConverter.boardCoordinateToIndex(a, b)];
    }

    /**
     * Access a Tile of a board by a BoardCoordinate Class
     *
     * @param boardCoordinate well ...
     */
    public Tile getTile(BoardCoordinate boardCoordinate) {
        return getTile(boardCoordinate.getI());
    }
}
