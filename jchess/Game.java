package jchess;


import jchess.mvc.Controller;
import jchess.mvc.events.SelectEvent;
import jchess.mvc.events.UpdateBoardEvent;
import jchess.util.BoardCoordinate;
import jchess.pieces.Piece;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;

/**
 * Created by andreas on 07.12.14.
 */
@Listener(references = References.Strong)
public class Game {

    private Player[] players;

    private Board board;

    private Game(Player[] players) {
        Controller.INSTANCE.subscribe(this);
        this.players = players;
        board = new Board();
    }

    public static Game newGame(Player[] players) throws IllegalArgumentException {
        if (players.length != 3) {
            throw new IllegalArgumentException();
        }
        Game game = new Game(players);
        return game;
    }

    public Board getBoard() {
        return board;
    }

    // <--
    public void invalidSelectEvent() {
    }

    // <--
    public BoardCoordinate[] possibleMoveEvent() {
        return new BoardCoordinate[0];
    }

    // <--
    public Piece[] selectPromotionEvent() {
        return new Piece[0];
    }

    // <--
    public void emitUpdateBoardEvent() {
        UpdateBoardEvent updateBoardEvent = new UpdateBoardEvent(board);
        Logging.GAME.debug("Emit  UpdateBoardEvent");
        updateBoardEvent.emit();
    }

    // -->
    @Handler
    public void handleSelectEvent(SelectEvent selectEvent) {
        if (selectEvent.isVisitedIOSystem()) {
            Logging.GAME.debug("Received Select Event");
            emitUpdateBoardEvent();
        }
    }

    // -->
    @Handler
    public void handlePromotionEvent(Piece piece) {
    }

}
