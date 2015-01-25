package jchess.game.board;

import jchess.util.BoardCoordinate;

/**
 * Tile Filter for easily adding figures and pieces
 * <p/>
 * Created by Elliot on 2014-12-06.
 *
 * @trace [$REQ42]
 */
public class TileFilter {
    private final BoardCoordinate[] repeat;
    private final BoardCoordinate[] single;
    private final BoardCoordinate[] singleKill;

    public TileFilter(BoardCoordinate[] repeat, BoardCoordinate[] single, BoardCoordinate[] singleKill) {
        this.repeat = repeat;
        this.single = single;
        this.singleKill = singleKill;
    }

    public BoardCoordinate[] getRepeat() {
        return repeat;
    }

    public BoardCoordinate[] getSingle() {
        return single;
    }

    public BoardCoordinate[] getSingleKill() {
        return singleKill;
    }
}
