package jchess.game.board;

import jchess.game.pieces.Piece;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Elliot on 2014-12-06.
 */
public class Tile {

    private Piece piece;
    private final Set<Integer> promotesPlayers;

    public Tile() {
        piece = null;
        promotesPlayers = new HashSet<>();
    }

    /**
     * Access a Piece on a Tile. Does not alter the state of that Tile.
     * Used to query if a Piece is on this Tile
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Places a Piece onto this Tile
     * Returns the Piece which was on this Tile before
     * or null if no Piece was there
     *
     * @param newPiece the Piece which is to be placed
     */
    public Piece placePiece(Piece newPiece) {
        Piece oldPiece = this.piece;
        this.piece = newPiece;
        return oldPiece;
    }

    /**
     * Removes a Piece from this Tile.
     * Returns either the old Piece or null
     */
    public Piece removePiece() {
        Piece oldPiece = this.piece;
        this.piece = null;
        return oldPiece;
    }

    /**
     * Registers this Field as promoting Tile ( in case a Pawn( playerID ) reaches this Tile )
     *
     * @param playerID
     */
    public void addPromotionForPlayer(int playerID) {
        promotesPlayers.add(playerID);
    }

    /**
     * Registers this Field as promoting Tile ( in case a Pawn( playerID ) reaches this Tile )
     */
    public boolean isPromotionTileFor(int playerID) {
        return promotesPlayers.contains(playerID);
    }
}
