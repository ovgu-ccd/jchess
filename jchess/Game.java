package jchess;


import jchess.mvc.events.UpdateBoardEvent;
import jchess.util.BoardCoordinate;
import jchess.pieces.Piece;
import net.engio.mbassy.listener.Handler;

/**
 * Created by andreas on 07.12.14.
 */
public class Game {

    private Player[] players;
    private Board board;

    private Game(Player[] players) {
        this.players = players;
        board = new Board();
    }

    public static Game newGame(Player[] players) throws IllegalArgumentException {
        if (players.length != 3) {
            throw new IllegalArgumentException();
        }
        Game g = new Game(players);
        return g;
    }


    // <--
    public void invalidSelectEvent() {}

    // <--
    public BoardCoordinate[] possibleMoveEvent() {
        return new BoardCoordinate[0] ;
    }

    // <--
    public Piece[] selectPromotionEvent() {
        return new Piece[0];
    }
    // <--
    public void emitUpdateBoardEvent() {
        UpdateBoardEvent updateBoardEvent = new UpdateBoardEvent( this );
        updateBoardEvent.emit();
    }

    // -->
    @Handler
    public void handleSelectedEvent( BoardCoordinate coordinate ) {

    }

    // -->
    @Handler public void handlePromotionEvent( Piece piece ) {}

}
