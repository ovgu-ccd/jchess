package jchess;


import jchess.mvc.Controller;
import jchess.mvc.events.InvalidSelectEvent;
import jchess.mvc.events.PossibleMovesEvent;
import jchess.mvc.events.SelectEvent;
import jchess.mvc.events.UpdateBoardEvent;
import jchess.pieces.Piece;
import jchess.util.BoardCoordinate;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by andreas on 07.12.14.
 */
@Listener(references = References.Strong)
public class Game {

    private Player[] players;
    private Board board;
    private WeakReference<Player> activePlayer;

    private Game(Player[] players) {
        Controller.INSTANCE.subscribe(this);
        this.players = players;
        this.board = new Board(this);
        emitUpdateBoardEvent();
    }

    public static Game newGame(Player[] players) throws IllegalArgumentException {
        if (players.length != 3) {
            throw new IllegalArgumentException();
        }
        Game game = new Game(players);
        for (Player player : players){
            player.setGame(game);
        }
        return game;
    }

    public Board getBoard() {
        return board;
    }

    public void emitInvalidSelectEvent() {
        InvalidSelectEvent invalidSelectEvent = new InvalidSelectEvent(this);
        Logging.GAME.debug("Game: Emit PossibleMovesEvent");
        invalidSelectEvent.emit();
    }

    public void emitPossibleMovesEvent() {
        PossibleMovesEvent possibleMovesEvent = new PossibleMovesEvent(this, collectPossibleMovesCoordinates());
        Logging.GAME.debug("Game: Emit PossibleMovesEvent");
        possibleMovesEvent.emit();
    }

    public Piece[] selectPromotionEvent() {
        return new Piece[0];
    }

    public void emitUpdateBoardEvent() {
        UpdateBoardEvent updateBoardEvent = new UpdateBoardEvent(this);
        Logging.GAME.debug("Game: Emit UpdateBoardEvent");
        updateBoardEvent.emit();
    }

    @Handler
    public void handleSelectEvent(SelectEvent selectEvent) {
        if (selectEvent.shouldReceive(selectEvent.getGame())) {
            // TODO logic
            Logging.GAME.debug("Game: Received SelectEvent");
            emitPossibleMovesEvent();
        }
    }

    @Handler
    public void handlePromotionEvent(Piece piece) {
    }

    private Set<BoardCoordinate> collectPossibleMovesCoordinates() {
        // TODO
        return new HashSet<BoardCoordinate>();
    }
}
