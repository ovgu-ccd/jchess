package jchess;


import jchess.mvc.events.UpdateBoardEvent;
import jchess.util.BoardCoordinate;
import jchess.pieces.Piece;
import net.engio.mbassy.listener.Handler;

/**
 * Created by andreas on 07.12.14.
 */
public class Game {
    private IOSystem[] ioSystems;
    private Board board;

    private Game(IOSystem[] ioSystems){
        this.ioSystems = ioSystems;
        board = new Board();
    }


    public static Game newGame(IOSystem[] ioSystems) throws  IllegalArgumentException{
        if (ioSystems.length != 3){
            throw new IllegalArgumentException();
        }
        Game g = new Game(ioSystems);
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
