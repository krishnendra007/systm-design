class GameEngine {

    public int calculateNewPosition(int current, int diceValue, int boardSize) {
        int next = current + diceValue;

        // Exact win rule
        if (next > boardSize) return current;

        return next;
    }

    public int applyJump(Board board, int position) {
        int newPosition = board.getFinalPosition(position);

        // Prevent chaining (important edge case)
        return newPosition;
    }

    public boolean hasWon(int position, int boardSize) {
        return position == boardSize;
    }
}