import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<Jump> jumps = Arrays.asList(
                new Jump(14, 7),   // Snake
                new Jump(31, 26),  // Snake
                new Jump(3, 22),   // Ladder
                new Jump(5, 8),    // Ladder
                new Jump(11, 26)   // Ladder
        );

        Board board = new Board(100, jumps);
        Dice dice = new Dice(1, 6);

        List<Player> players = Arrays.asList(
                new Player("1", "Alice"),
                new Player("2", "Bob")
        );

        Game game = new Game(board, dice, players);

        game.start();

        while (!game.isFinished()) {
            game.playTurn();
        }
    }
}