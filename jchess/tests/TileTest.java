package jchess.tests;

/**
 * Created by Elliot on 2015-01-24.
 */

import jchess.game.board.Tile;
import jchess.game.pieces.King;
import jchess.game.pieces.Pawn;
import jchess.game.pieces.Queen;
import jchess.game.pieces.Rook;
import org.junit.Before;
import org.junit.Test;

//import static org.junit.Assert.*;

public class TileTest {

    private Tile tile;

    @Before
    public void setup() {
        tile = new Tile();
    }

    @Test
    public void testGetPiece() {
        assert( tile.getPiece() == null );
        tile.placePiece( new King(0) );
        assert( tile.getPiece() != null );
        assert( tile.getPiece() instanceof King );
    }

    @Test
    public void testPlacePiece() {
        assert( tile.placePiece( new Queen(1)) == null );
        assert( tile.placePiece( new Rook(2)) instanceof Queen );
        assert( tile.placePiece( new Pawn(0)) instanceof Rook );
    }

    @Test
    public void testRemovePiece() {
        assert( tile.removePiece() == null );
        tile.placePiece(new King(0));
        assert( tile.removePiece() instanceof King );
        assert( tile.getPiece() == null );
    }

    @Test
    public void testPromotion() {
        assert( tile.isPromotionTileFor(1) == false  );
        tile.addPromotionForPlayer(0);
        assert( tile.isPromotionTileFor(0) == true  );
        assert( tile.isPromotionTileFor(1) == false  );
        assert( tile.isPromotionTileFor(2) == false  );
        tile.addPromotionForPlayer(2);
        assert( tile.isPromotionTileFor(0) == true  );
        assert( tile.isPromotionTileFor(1) == false  );
        assert( tile.isPromotionTileFor(2) == true  );
    }
}
