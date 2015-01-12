package jchess.pieces;

/**
 * Created by andreas on 12.01.15.
 */
public enum Pieces {
    BISHOP(Bishop.class),
    KING(King.class),
    KNIGHT(Knight.class),
    PAWN(Pawn.class),
    QUEEN(Queen.class),
    ROOK(Rook.class);

    private final Class<? extends Piece> piece;

    Pieces(Class<? extends Piece> piece) {
        this.piece = piece;
    }

    public Class<? extends Piece> getPiece() {
        return piece;
    }

    public String toString() {
        return piece.getSimpleName();
    }
}
