package jchess;

import jchess.mvc.Controller;
import jchess.mvc.events.InvalidSelectEvent;
import jchess.mvc.events.PossibleMovesEvent;
import jchess.mvc.events.SelectEvent;
import jchess.mvc.events.UpdateBoardEvent;
import jchess.pieces.Pawn;
import jchess.pieces.Piece;
import jchess.pieces.Rook;
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
    private BoardCoordinate selectedBC;
    private int activePlayerID;
    private HashSet<BoardCoordinate> possibleMovesCoordinates;

    private Game(Player[] players) {
        Controller.INSTANCE.subscribe(this);
        this.players = players;
        this.board = new Board();
        this.possibleMovesCoordinates = new HashSet<>();
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
            selectedBC = selectEvent.getBoardCoordinate();
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
        possibleMovesCoordinates.clear();

        Piece piece = selectedTile.getPiece() ;
        // repeat
        for( BoardCoordinate repeatBC : piece.getTileFilter().getRepeat() )  {
            BoardCoordinate resultBC = new BoardCoordinate( selectedBC.getA() + repeatBC.getA() , selectedBC.getB() + repeatBC.getB() ) ;
            while(  ( resultBC.getA() >=  0 && resultBC.getA() < 15 ) &&
                    ( resultBC.getB() >=  0 && resultBC.getB() < 15 ) &&
                    ( resultBC.getC() >= -7 && resultBC.getC() <= 7 )   ) {

                // Need to know if any piece is in the possible move trajectory
                Piece pieceOnResultBC = board.getTile( resultBC ).getPiece();

                // Stop collecting tiles if another activePlayer piece is in the trajectory
                if ( pieceOnResultBC != null && activePlayerID == pieceOnResultBC.getPlayerID() ) break;

                possibleMovesCoordinates.add( resultBC );

                // if we got here and pieceOnResultBC is not null, pieceOnResultBC is an enemy
                if ( pieceOnResultBC != null ) break;

                // continue trajectory with new BoardCoordinates
                resultBC = new BoardCoordinate( resultBC.getA() + repeatBC.getA() , resultBC.getB() + repeatBC.getB() ) ;
            }
        }

        // single
        for( BoardCoordinate singleBC : piece.getTileFilter().getSingle() ) {
            BoardCoordinate resultBC = new BoardCoordinate(selectedBC.getA() + singleBC.getA(), selectedBC.getB() + singleBC.getB());
            if (    ( resultBC.getA() >=  0 && resultBC.getA() < 15 ) &&
                    ( resultBC.getB() >=  0 && resultBC.getB() < 15 ) &&
                    ( resultBC.getC() >= -7 && resultBC.getC() <= 7 )) {

                // Need to know if any piece is in the possible move tile
                Piece pieceOnResultBC = board.getTile(resultBC).getPiece();

                // Don't collect tile if another activePlayer piece is on it
                if ( pieceOnResultBC != null )
                    if ( piece instanceof Pawn || activePlayerID == pieceOnResultBC.getPlayerID() )
                        continue;                     // Pawn is a special case and cannot kill in its walking trajectory

                possibleMovesCoordinates.add(resultBC);
            }
        }

        // singleKill - Pawn only
        for( BoardCoordinate singleKillBC : piece.getTileFilter().getSingleKill() ) {
            BoardCoordinate resultBC = new BoardCoordinate(selectedBC.getA() + singleKillBC.getA(), selectedBC.getB() + singleKillBC.getB());
            if ((resultBC.getA() >= 0 && resultBC.getA() < 15) &&
                    (resultBC.getB() >= 0 && resultBC.getB() < 15) &&
                    (resultBC.getC() >= -7 && resultBC.getC() <= 7)) {

                // Need to know if any piece is in the possible kill tile
                Piece pieceOnResultBC = board.getTile(resultBC).getPiece();

                // Only collect tile if another players piece is on it
                if ( pieceOnResultBC != null && activePlayerID != pieceOnResultBC.getPlayerID() )
                    possibleMovesCoordinates.add(resultBC);
            }
        }


        //possibleMovesCoordinates.add(new BoardCoordinate(5, 5));
        //possibleMovesCoordinates.add(new BoardCoordinate(6, 6));
        //possibleMovesCoordinates.add(new BoardCoordinate(7, 7));

    }
}
