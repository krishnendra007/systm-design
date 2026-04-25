import java.util.*;

class Game {

    private final Board board;
    private final Dice dice;
    private final TurnManager turnManager;
    private final GameEngine engine;

    private GameState state;
    private Player winner;

    public Game(Board board, Dice dice, List<Player> players) {
        if (players.size() < 2) {
            throw new RuntimeException("Minimum 2 players required");
        }

        this.board = board;
        this.dice = dice;
        this.turnManager = new TurnManager(players);
        this.engine = new GameEngine();
        this.state = GameState.NOT_STARTED;
    }

    public void start() {
        state = GameState.IN_PROGRESS;
    }

    public void playTurn() {
        if (state != GameState.IN_PROGRESS) {
            throw new RuntimeException("Game not in progress");
        }

        Player player = turnManager.getCurrentPlayer();
        int diceValue = dice.roll();

        System.out.println(player.getName() + " rolled: " + diceValue);

        int newPosition = engine.calculateNewPosition(
                player.getPosition(),
                diceValue,
                board.getSize()
        );

        newPosition = engine.applyJump(board, newPosition);

        player.setPosition(newPosition);

        System.out.println(player.getName() + " moved to: " + newPosition);

        if (engine.hasWon(newPosition, board.getSize())) {
            state = GameState.FINISHED;
            winner = player;
            System.out.println("Winner is: " + player.getName());
            return;
        }

        // Extra turn if 6 (configurable rule)
        if (diceValue != 6) {
            turnManager.moveToNext();
        }
    }

    public boolean isFinished() {
        return state == GameState.FINISHED;
    }

    public Player getWinner() {
        return winner;
    }
}