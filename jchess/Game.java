package jchess;

/**
 * Created by andreas on 07.12.14.
 */
public class Game {
    private Player[] players;

    private Game(Player[] players) {
        this.players = players;
    }

    public static Game newGame(Player[] players) throws IllegalArgumentException {
        if (players.length != 3) {
            throw new IllegalArgumentException();
        }

        return new Game(players);
    }
}
