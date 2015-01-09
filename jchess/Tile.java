package jchess;

import jchess.pieces.Piece;

/**
 * Created by Elliot on 2014-12-06.
 */
public class Tile {

    private Tile neighbors[] ;
    private Piece piece;

    Tile()  {
        neighbors = null;
        piece = null;
    }

    public void setNeigbors( Tile neighbors[] ) {
        this.neighbors = neighbors;
    }

    public Tile getNorth() {
        return neighbors[0];
    }

    public Tile getNorthEast() {
        return neighbors[1];
    }

    public Tile getSouthEast() {
        return neighbors[2];
    }

    public Tile getSouth() {
        return neighbors[3];
    }

    public Tile getSouthWest() {
        return neighbors[4];
    }

    public Tile getNorthWest() {
        return neighbors[5];
    }

    Piece getPiece() {
        return this.piece;
    }

    Piece placePiece( Piece newPiece ) {
        Piece oldPiece = this.piece;
        this.piece = newPiece;
        return oldPiece;
    }

    Piece removePiece() {
        Piece oldPiece = this.piece;
        this.piece = null;
        return oldPiece;
    }
}
