import java.util.*;

class Board {
    private final int size;
    private final Map<Integer, Jump> jumps;

    public Board(int size, List<Jump> jumpsList) {
        this.size = size;
        this.jumps = new HashMap<>();

        for (Jump jump : jumpsList) {
            validateJump(jump);
            if (jumps.containsKey(jump.getStart())) {
                throw new RuntimeException("Duplicate jump at position: " + jump.getStart());
            }
            jumps.put(jump.getStart(), jump);
        }
    }

    private void validateJump(Jump jump) {
        if (jump.getStart() <= 0 || jump.getStart() > size ||
                jump.getEnd() <= 0 || jump.getEnd() > size) {
            throw new RuntimeException("Invalid jump positions");
        }
    }

    public int getSize() {
        return size;
    }

    public int getFinalPosition(int position) {
        if (jumps.containsKey(position)) {
            return jumps.get(position).getEnd();
        }
        return position;
    }
}