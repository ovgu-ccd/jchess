package jchess;

import jchess.pieces.Piece;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Elliot on 2014-12-06.
 */
public class Tile {

    private Piece piece;
    private Set<Integer> promotesPlayers;

    public Tile()  {
        piece = null;
        promotesPlayers = new HashSet<>();
    }

    public Piece getPiece() {
        return this.piece;
    }

    public Piece placePiece( Piece newPiece ) {
        Piece oldPiece = this.piece;
        this.piece = newPiece;
        return oldPiece;
    }

    public Piece removePiece() {
        Piece oldPiece = this.piece;
        this.piece = null;
        return oldPiece;
    }

    public void addPromotionForPlayer(int playerID) {
        promotesPlayers.add(playerID);
    }

    public boolean isPromotionTileFor(int playerID) {
        return promotesPlayers.contains(playerID);
    }
}
