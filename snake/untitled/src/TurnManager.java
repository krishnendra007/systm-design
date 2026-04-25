import java.util.*;

class TurnManager {
    private final Queue<Player> players;

    public TurnManager(List<Player> playersList) {
        this.players = new LinkedList<>(playersList);
    }

    public Player getCurrentPlayer() {
        return players.peek();
    }

    public void moveToNext() {
        Player p = players.poll();
        players.offer(p);
    }
}