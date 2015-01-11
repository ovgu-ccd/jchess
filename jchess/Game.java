package jchess;

import jchess.mvc.Controller;
import jchess.mvc.events.InvalidSelectEvent;
import jchess.mvc.events.PossibleMovesEvent;
import jchess.mvc.events.SelectEvent;
import jchess.mvc.events.UpdateBoardEvent;
import jchess.pieces.Piece;
import jchess.util.BoardCoordinate;
import jchess.util.CoordinateConverter;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;

import java.util.HashSet;


/**
 * Created by andreas on 07.12.14.
 */
@Listener(references = References.Strong)
public class Game {

    private Player[] players;
    private Board board;
    private Tile selectedTile;
    private int activePlayerID;
    private HashSet<BoardCoordinate> possibleMovesCoordinates;

    private Game(Player[] players) {
        Controller.INSTANCE.subscribe(this);
        this.players = players;
        this.board = new Board();
    }

    public static Game newGame(Player[] players) throws IllegalArgumentException {
        if (players.length != 3) {
            throw new IllegalArgumentException();
        }
        Game game = new Game(players);
        for (Player player : players) {
            player.setGame(game);
        }
        return game;
    }

    public Board getBoard() {
        return board;
    }

    public void emitInvalidSelectEvent() {
        InvalidSelectEvent invalidSelectEvent = new InvalidSelectEvent(this);
        Logging.GAME.debug("Game: Emit InvalidSelectEvent");
        invalidSelectEvent.emit();
    }

    public void emitPossibleMovesEvent() {
        collectPossibleMoveCoordinates();
        PossibleMovesEvent possibleMovesEvent = new PossibleMovesEvent(this, possibleMovesCoordinates);
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
        if (selectEvent.shouldReceive(this)) {
            Logging.GAME.debug(selectEvent.getGame() + " Received SelectEvent");
            if (selectedTile == null) {
                Tile tile = board.getTile(selectEvent.getBoardCoordinate().getI());
                if (tile.getPiece() == null || !players[tile.getPiece().getPlayerID()].isActive()) {
                    emitInvalidSelectEvent();
                } else {
                    selectedTile = tile;
                    emitPossibleMovesEvent();
                }
            } else {
                Tile tile = board.getTile(selectEvent.getBoardCoordinate().getI());

                if (tile.getPiece() == null || !players[tile.getPiece().getPlayerID()].isActive()) {
                    if (possibleMovesCoordinates.contains(selectEvent.getBoardCoordinate())) {
                        Piece selectedPiece = selectedTile.getPiece();
                        selectedTile.removePiece();
                        tile.placePiece(selectedPiece);

                        activePlayerID++;
                        activePlayerID %= 3;
                        for (int i = 0; i < 3; i++) {
                            players[i].setActive(i == activePlayerID);
                        }

                        selectedTile = null;
                        emitUpdateBoardEvent();
                    } else {
                        emitInvalidSelectEvent();
                    }
                } else {
                    selectedTile = tile;
                    emitPossibleMovesEvent();
                }
            }
        }
    }

    @Handler
    public void handlePromotionEvent(Piece piece) {
    }

    private void collectPossibleMoveCoordinates() {
        possibleMovesCoordinates = new HashSet<>();
        possibleMovesCoordinates.add(new BoardCoordinate(0, 1, 1));
        possibleMovesCoordinates.add(new BoardCoordinate(1, 1, 1));
        possibleMovesCoordinates.add(new BoardCoordinate(1, 2, 1));
    }
}
