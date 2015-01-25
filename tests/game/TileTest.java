package tests.game;

/**
 * Created by Elliot on 2015-01-24.
 */

import jchess.game.board.Tile;

import jchess.game.pieces.*;
import org.junit.Test;

public class TileTest {

    @Test
    public void testGetPiece() {
        Tile tile = new Tile();
        assert( tile.getPiece() == null );
        tile.placePiece( new King(0) );
        assert( tile.getPiece() != null );
        assert( tile.getPiece() instanceof King );
    }

    @Test
    public void testPlacePiece() {
        Tile tile = new Tile();
        Queen queen = new Queen(1);
        Piece placeQueen = tile.placePiece(queen);
        assert( placeQueen == null );
        Rook rook = new Rook(2);
        Piece placeRook = tile.placePiece(rook);
        assert( placeRook instanceof Queen );
        Pawn pawn = new Pawn(0);
        Piece placePawn = tile.placePiece(pawn);
        assert( placePawn instanceof Rook );
    }

    @Test
    public void testRemovePiece() {
        Tile tile = new Tile();
        Piece removePiece = tile.removePiece();
        assert( removePiece == null );
        tile.placePiece(new King(0));
        Piece removeKing = tile.removePiece();
        assert( removeKing instanceof King );
        assert( tile.getPiece() == null );
    }

    @Test
    public void testPromotion() {
        Tile tile = new Tile();
        assert(!tile.isPromotionTileFor(1));
        tile.addPromotionForPlayer(0);
        assert(tile.isPromotionTileFor(0));
        assert(!tile.isPromotionTileFor(1));
        assert(!tile.isPromotionTileFor(2));
        tile.addPromotionForPlayer(2);
        assert(tile.isPromotionTileFor(0));
        assert(!tile.isPromotionTileFor(1));
        assert(tile.isPromotionTileFor(2));
    }
}
