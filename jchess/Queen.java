/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package jchess;

import jchess.gui.Chessboard;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class to represent a queen piece
 * Queen can move almost in every way:
 * |_|_|_|X|_|_|_|X|7
    |X|_|_|X|_|_|X|_|6
    |_|X|_|X|_|X|_|_|5
    |_|_|X|X|x|_|_|_|4
    |X|X|X|Q|X|X|X|X|3
    |_|_|X|X|X|_|_|_|2
    |_|X|_|X|_|X|_|_|1
    |X|_|_|X|_|_|X|_|0
    0 1 2 3 4 5 6 7
 */
public class Queen extends Piece {

    public static short value = 9;
    protected static final Image imageWhite = GUI.loadImage("Queen-W.png");
    protected static final Image imageBlack = GUI.loadImage("Queen-B.png");

    public Queen(Chessboard chessboard, Player player) {
        super(chessboard, player);//call initializer of super type: Piece
        //this.setImages("Queen-W.png", "Queen-B.png");
        this.symbol = "Q";
        this.setImage();
    }

    @Override
    void setImage() {
        if (this.player.getColor() == this.player.getColor().black) {
            image = imageBlack;
        } else {
            image = imageWhite;
        }
        orgImage = image;
    }

    /**
     * Annotation to superclass Piece changing pawns location
     * @return  ArrayList with new possition of piece
     */
    @Override
    public ArrayList allMoves() {
        ArrayList list = new ArrayList();

        // ------------- as Rook --------------
        for (int i = this.square.getPozY() + 1; i <= 7; ++i) {
            //up

            if (this.checkPiece(this.square.getPozX(), i)) {
                //if on this sqhuare isn't piece

                if (this.player.getColor() == Player.colors.white) {
                    //white

                    if (this.getChessboard().kingWhite.willBeSafeWhenMoveOtherPiece(this.square, getChessboard().squares[this.square.getPozX()][i])) {
                        list.add(getChessboard().squares[this.square.getPozX()][i]);
                    }
                } else {
                    //or black

                    if (this.getChessboard().kingBlack.willBeSafeWhenMoveOtherPiece(this.square, getChessboard().squares[this.square.getPozX()][i])) {
                        list.add(getChessboard().squares[this.square.getPozX()][i]);
                    }
                }

                if (this.otherOwner(this.square.getPozX(), i)) {
                    break;
                }
            } else { //if on this square is piece
                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int i = this.square.getPozY() - 1; i >= 0; --i) {
            //down

            if (this.checkPiece(this.square.getPozX(), i)) {
                //if on this sqhuare isn't piece

                if (this.player.getColor() == Player.colors.white) {
                    //white

                    if (this.getChessboard().kingWhite.willBeSafeWhenMoveOtherPiece(this.square, getChessboard().squares[this.square.getPozX()][i])) {
                        list.add(getChessboard().squares[this.square.getPozX()][i]);
                    }
                } else {
                    //or black

                    if (this.getChessboard().kingBlack.willBeSafeWhenMoveOtherPiece(this.square, getChessboard().squares[this.square.getPozX()][i])) {
                        list.add(getChessboard().squares[this.square.getPozX()][i]);
                    }
                }

                if (this.otherOwner(this.square.getPozX(), i)) {
                    break;
                }
            } else {
                //if on this square is piece

                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int i = this.square.getPozX() - 1; i >= 0; --i) {
            //left

            if (this.checkPiece(i, this.square.getPozY())) {
                //if on this sqhuare isn't piece

                if (this.player.getColor() == Player.colors.white) {
                    //white

                    if (this.getChessboard().kingWhite.willBeSafeWhenMoveOtherPiece(this.square, getChessboard().squares[i][this.square.getPozY()])) {
                        list.add(getChessboard().squares[i][this.square.getPozY()]);
                    }
                } else {
                    //or black

                    if (this.getChessboard().kingBlack.willBeSafeWhenMoveOtherPiece(this.square, getChessboard().squares[i][this.square.getPozY()])) {
                        list.add(getChessboard().squares[i][this.square.getPozY()]);
                    }
                }

                if (this.otherOwner(i, this.square.getPozY())) {
                    break;
                }
            } else {
                //if on this square is piece

                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int i = this.square.getPozX() + 1; i <= 7; ++i) {
            //right

            if (this.checkPiece(i, this.square.getPozY())) {
                //if on this sqhuare isn't piece

                if (this.player.getColor() == Player.colors.white) {
                    //white

                    if (this.getChessboard().kingWhite.willBeSafeWhenMoveOtherPiece(this.square, getChessboard().squares[i][this.square.getPozY()])) {
                        list.add(getChessboard().squares[i][this.square.getPozY()]);
                    }
                } else {
                    //or black

                    if (this.getChessboard().kingBlack.willBeSafeWhenMoveOtherPiece(this.square, getChessboard().squares[i][this.square.getPozY()])) {
                        list.add(getChessboard().squares[i][this.square.getPozY()]);
                    }
                }

                if (this.otherOwner(i, this.square.getPozY())) {
                    break;
                }
            } else {
                //if on this square is piece

                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        // ------------- as Bishop ------------------
        for (int h = this.square.getPozX() - 1, i = this.square.getPozY() + 1; !isout(h, i); --h, ++i) {
            //left-up

            if (this.checkPiece(h, i)) {
                //if on this sqhuare isn't piece

                if (this.player.getColor() == Player.colors.white) {
                    //white

                    if (this.getChessboard().kingWhite.willBeSafeWhenMoveOtherPiece(this.square, getChessboard().squares[h][i])) {
                        list.add(getChessboard().squares[h][i]);
                    }
                } else {
                    //or black
                    if (this.getChessboard().kingBlack.willBeSafeWhenMoveOtherPiece(this.square, getChessboard().squares[h][i])) {
                        list.add(getChessboard().squares[h][i]);
                    }
                }

                if (this.otherOwner(h, i)) {
                    break;
                }
            } else {
                //if on this square is piece

                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int h = this.square.getPozX() - 1, i = this.square.getPozY() - 1; !isout(h, i); --h, --i) {
            //left-down

            if (this.checkPiece(h, i)) {
                //if on this sqhuare isn't piece

                if (this.player.getColor() == Player.colors.white) {
                    //white

                    if (this.getChessboard().kingWhite.willBeSafeWhenMoveOtherPiece(this.square, getChessboard().squares[h][i])) {
                        list.add(getChessboard().squares[h][i]);
                    }
                } else {
                    //or black

                    if (this.getChessboard().kingBlack.willBeSafeWhenMoveOtherPiece(this.square, getChessboard().squares[h][i])) {
                        list.add(getChessboard().squares[h][i]);
                    }
                }

                if (this.otherOwner(h, i)) {
                    break;
                }
            } else {
                //if on this square is piece

                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int h = this.square.getPozX() + 1, i = this.square.getPozY() + 1; !isout(h, i); ++h, ++i) {
            //right-up

            if (this.checkPiece(h, i)) {
                //if on this sqhuare isn't piece

                if (this.player.getColor() == Player.colors.white) {
                    //white

                    if (this.getChessboard().kingWhite.willBeSafeWhenMoveOtherPiece(this.square, getChessboard().squares[h][i])) {
                        list.add(getChessboard().squares[h][i]);
                    }
                } else {
                    //or black

                    if (this.getChessboard().kingBlack.willBeSafeWhenMoveOtherPiece(this.square, getChessboard().squares[h][i])) {
                        list.add(getChessboard().squares[h][i]);
                    }
                }

                if (this.otherOwner(h, i)) {
                    break;
                }
            } else {
                //if on this square is piece

                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int h = this.square.getPozX() + 1, i = this.square.getPozY() - 1; !isout(h, i); ++h, --i) {
            //right-down

            if (this.checkPiece(h, i)) {
                //if on this sqhuare isn't piece

                if (this.player.getColor() == Player.colors.white) {
                    //white

                    if (this.getChessboard().kingWhite.willBeSafeWhenMoveOtherPiece(this.square, getChessboard().squares[h][i])) {
                        list.add(getChessboard().squares[h][i]);
                    }
                } else {
                    //or black

                    if (this.getChessboard().kingBlack.willBeSafeWhenMoveOtherPiece(this.square, getChessboard().squares[h][i])) {
                        list.add(getChessboard().squares[h][i]);
                    }
                }

                if (this.otherOwner(h, i)) {
                    break;
                }
            } else {
                //if on this square is piece

                break;//we've to break becouse we cannot go beside other piece!!
            }
        }
        // ------------------------------------

        return list;
    }
}
