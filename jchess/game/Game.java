package jchess.game;

import com.google.inject.Inject;
import jchess.eventbus.Controller;
import jchess.eventbus.events.*;
import jchess.game.board.Board;
import jchess.game.board.InvalidBoardCoordinateException;
import jchess.game.board.Tile;
import jchess.game.pieces.*;
import jchess.util.BoardCoordinate;
import jchess.util.Logging;
import jchess.util.StringResources;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;


/**
 * Central game class holding players and state of game.
 * <p/>
 * Created by andreas on 07.12.14.
 *
 * @trace $REQ22
 * @trace $REQ23
 */
@Listener(references = References.Strong)
public class Game {

    private final Player[] players;
    @SuppressWarnings("UnusedDeclaration")
    @Inject
    private Board board;
    @SuppressWarnings("UnusedDeclaration")
    @Inject
    private HashSet<BoardCoordinate> possibleMovesCoordinates;
    @SuppressWarnings("UnusedDeclaration")
    @Inject
    private HashSet<Class<? extends Piece>> possiblePromotions;
    private Tile selectedTile;
    private Tile promotionTile;
    private BoardCoordinate selectedBC;
    private int activePlayerID;

    public Game() {
        Controller.INSTANCE.subscribe(this);
        players = new Player[3];
    }

    /**
     * Create players and initialize IOSystems for new game
     *
     * @param playerNames
     * @param ioSystems
     */
    public void initializeGame(String[] playerNames, IOSystem[] ioSystems) {
        if (playerNames.length != 3 || ioSystems.length != 3) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < 3; i++) {
            players[i] = new Player(playerNames[i], ioSystems[i], Player.PlayerColor.values()[i]);
            players[i].setGame(this);
            ioSystems[i].setPlayer(players[i]);
        }

        players[0].setActive(true);
        emitUpdateBoardEvent();
    }

    public Board getBoard() {
        return board;
    }

    /**
     * Return cached possible moves for last selected piece.
     *
     * @return
     */
    public HashSet<BoardCoordinate> getPossibleMovesCoordinates() {
        return possibleMovesCoordinates;
    }

    private void emitPossibleMovesEvent() {
        collectPossibleMoveCoordinates();
        PossibleMovesEvent possibleMovesEvent = new PossibleMovesEvent(this, possibleMovesCoordinates);
        Logging.GAME.debug("Game: Emit PossibleMovesEvent");
        possibleMovesEvent.emit();
    }

    private void emitPossiblePromotionsEvent() {
        collectPossiblePromotions();
        PossiblePromotionsEvent possiblePromotionsEvent = new PossiblePromotionsEvent(this, possiblePromotions);
        Logging.GAME.debug("Game: Emit PossiblePromotionsEvent");
        possiblePromotionsEvent.emit();
    }

    public void emitUpdateBoardEvent() {
        UpdateBoardEvent updateBoardEvent = new UpdateBoardEvent(this);
        Logging.GAME.debug("Game: Emit UpdateBoardEvent");
        updateBoardEvent.emit();
        emitUpdateStatusMessageEvent(players[activePlayerID].getName() + ": " + StringResources.MAIN.getString("StatusMessage.MakeYourMove"), UpdateStatusMessageEvent.Types.NORMAL);
    }

    private void emitUpdateStatusMessageEvent(String message, UpdateStatusMessageEvent.Types types) {
        UpdateStatusMessageEvent updateStatusMessageEvent = new UpdateStatusMessageEvent(this, message, types);
        Logging.GAME.debug("Game: Emit UpdateBoardEvent");
        updateStatusMessageEvent.emit();
    }

    @Handler
    public void handleSelectEvent(SelectEvent selectEvent) {
        if (selectEvent.shouldReceive(this)) {
            Logging.GAME.debug(selectEvent.getGame() + " Received SelectEvent");
            selectedBC = selectEvent.getBoardCoordinate();

            if (selectedTile == null) {
                // Currently no figure selected
                Tile tile = null;
                try {
                    tile = board.getTile(selectEvent.getBoardCoordinate().getI());
                } catch (InvalidBoardCoordinateException e) {
                    e.printStackTrace();
                }
                //noinspection ConstantConditions
                if (tile.getPiece() == null) {
                    // Empty Tile
                    emitUpdateStatusMessageEvent(players[activePlayerID].getName() + ": " + StringResources.MAIN.getString("StatusMessage.SelectAPiece"), UpdateStatusMessageEvent.Types.ALERT);
                } else if (!players[tile.getPiece().getPlayerID()].isActive()) {
                    // Enemy on Tile
                    emitUpdateStatusMessageEvent(players[activePlayerID].getName() + ": " + StringResources.MAIN.getString("StatusMessage.NotYourPiece"), UpdateStatusMessageEvent.Types.ALERT);
                } else {
                    // Successful Selection
                    selectedTile = tile;
                    emitPossibleMovesEvent();
                }
            } else {
                // Figure selected
                Tile tile = null;
                try {
                    tile = board.getTile(selectEvent.getBoardCoordinate().getI());
                } catch (InvalidBoardCoordinateException e) {
                    e.printStackTrace();
                }

                //noinspection ConstantConditions
                if (tile.getPiece() == null || !players[tile.getPiece().getPlayerID()].isActive()) {
                    // Successful move
                    if (possibleMovesCoordinates.contains(selectEvent.getBoardCoordinate())) {
                        Piece selectedPiece = selectedTile.getPiece();
                        selectedTile.removePiece();
                        Piece killedPiece = tile.placePiece(selectedPiece);


                        if (killedPiece != null && killedPiece instanceof King) {
                            defeatPlayer(killedPiece.getPlayerID());
                        }

                        selectedPiece.postMoveCallback();

                        nextPlayer();

                        for (int i = 0; i < 3; i++) {
                            players[i].setActive(i == activePlayerID);
                        }

                        selectedTile = null;
                        emitUpdateBoardEvent();

                        if (tile.isPromotionTileFor(selectedPiece.getPlayerID())) {
                            promotionTile = tile;
                            emitPossiblePromotionsEvent();
                        }
                    } else {
                        emitUpdateStatusMessageEvent(players[activePlayerID].getName() + ": " + StringResources.MAIN.getString("StatusMessage.CantGoThere"), UpdateStatusMessageEvent.Types.ALERT);
                    }
                } else {
                    selectedTile = tile;
                    emitPossibleMovesEvent();
                }
            }
        }
    }

    /**
     * Make next non-defeated player the new active player.
     * [$REQ40b]
     */
    private void nextPlayer() {
        do {
            activePlayerID++;
            activePlayerID %= 3;
        } while (players[activePlayerID].isDefeated());
    }

    /**
     * Remove all pieces of a player and mark them as defeated.
     * [$REQ40b]
     */
    private void defeatPlayer(int defeatedPlayerID) {
        players[defeatedPlayerID].setDefeated(true);
        for (int i = 0; i < board.getTileCount(); i++) {
            try {
                Tile tempTile = board.getTile(i);
                if (tempTile.getPiece() != null
                        && tempTile.getPiece().getPlayerID() == defeatedPlayerID) {
                    tempTile.removePiece();
                }
            } catch (InvalidBoardCoordinateException e) {
                e.printStackTrace();
            }
        }
        emitUpdateStatusMessageEvent(players[defeatedPlayerID].getName() +
                        ": " + StringResources.MAIN.getString("StatusMessage.Loose"),
                UpdateStatusMessageEvent.Types.ALERT);
    }

    @SuppressWarnings("UnusedDeclaration")
    @Handler
    public void handlePromotionSelectEvent(PromotionSelectEvent promotionSelectEvent) {
        if (promotionSelectEvent.shouldReceive(this)) {
            Logging.GAME.debug(promotionSelectEvent.getGame() + " Received PromotionSelectEvent");

            try {
                Piece piece = (Piece) promotionSelectEvent.getPieceClass().getConstructors()[0]
                        .newInstance(promotionTile.getPiece().getPlayerID());
                promotionTile.placePiece(piece);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | RuntimeException e) {
                e.printStackTrace();
                (new GenericErrorEvent(this, e)).emit();
            }
            emitUpdateBoardEvent();
        }
    }

    private void collectPossibleMoveCoordinates() {
        possibleMovesCoordinates.clear();

        Piece piece = selectedTile.getPiece();
        // repeat
        for (BoardCoordinate repeatBC : piece.getTileFilter().getRepeat()) {
            BoardCoordinate resultBC = new BoardCoordinate(selectedBC.getA() + repeatBC.getA(), selectedBC.getB() + repeatBC.getB());
            while (resultBC.getA() >= 0 &&
                    resultBC.getA() < 15 &&
                    resultBC.getB() >= 0 &&
                    resultBC.getB() < 15 &&
                    resultBC.getC() >= -7 &&
                    resultBC.getC() <= 7) {

                // Need to know if any piece is in the possible move trajectory
                Piece pieceOnResultBC = null;
                try {
                    pieceOnResultBC = board.getTile(resultBC).getPiece();
                } catch (InvalidBoardCoordinateException e) {
                    e.printStackTrace();
                }

                // Stop collecting tiles if another activePlayer piece is in the trajectory
                if (pieceOnResultBC != null && activePlayerID == pieceOnResultBC.getPlayerID()) break;

                possibleMovesCoordinates.add(resultBC);

                // if we got here and pieceOnResultBC is not null, pieceOnResultBC is an enemy
                if (pieceOnResultBC != null) break;

                // continue trajectory with new BoardCoordinates
                resultBC = new BoardCoordinate(resultBC.getA() + repeatBC.getA(), resultBC.getB() + repeatBC.getB());
            }
        }

        // single
        for (BoardCoordinate singleBC : piece.getTileFilter().getSingle()) {
            BoardCoordinate resultBC = new BoardCoordinate(selectedBC.getA() + singleBC.getA(), selectedBC.getB() + singleBC.getB());
            if (resultBC.getA() >= 0 &&
                    resultBC.getA() < 15 &&
                    resultBC.getB() >= 0 &&
                    resultBC.getB() < 15 &&
                    resultBC.getC() >= -7 &&
                    resultBC.getC() <= 7) {

                // Don't collect tile if another activePlayer piece is on it
                Piece pieceOnResultBC = null;
                try {
                    pieceOnResultBC = board.getTile(resultBC).getPiece();
                } catch (InvalidBoardCoordinateException e) {
                    e.printStackTrace();
                }
                if (pieceOnResultBC == null)
                    possibleMovesCoordinates.add(resultBC);
            }
        }

        // singleKill
        for (BoardCoordinate singleKillBC : piece.getTileFilter().getSingleKill()) {
            BoardCoordinate resultBC = new BoardCoordinate(selectedBC.getA() + singleKillBC.getA(), selectedBC.getB() + singleKillBC.getB());
            if (resultBC.getA() >= 0 &&
                    resultBC.getA() < 15 &&
                    resultBC.getB() >= 0 &&
                    resultBC.getB() < 15 &&
                    resultBC.getC() >= -7 &&
                    resultBC.getC() <= 7) {

                // Only collect tile if another players piece is on it
                Piece pieceOnResultBC = null;
                try {
                    pieceOnResultBC = board.getTile(resultBC).getPiece();
                } catch (InvalidBoardCoordinateException e) {
                    e.printStackTrace();
                }
                if (pieceOnResultBC != null && activePlayerID != pieceOnResultBC.getPlayerID())
                    possibleMovesCoordinates.add(resultBC);
            }
        }
    }

    private void collectPossiblePromotions() {
        possiblePromotions.clear();

        possiblePromotions.add(Queen.class);
        possiblePromotions.add(Bishop.class);
        possiblePromotions.add(Knight.class);
        possiblePromotions.add(Rook.class);
    }
}
