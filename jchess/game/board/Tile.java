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

    public Piece getPiece() {
        return this.piece;
    }

    public void placePiece(Piece newPiece) {
        this.piece = newPiece;
    }

    public void removePiece() {
        this.piece = null;
    }

    public void addPromotionForPlayer(int playerID) {
        promotesPlayers.add(playerID);
    }

    public boolean isPromotionTileFor(int playerID) {
        return promotesPlayers.contains(playerID);
    }
}
