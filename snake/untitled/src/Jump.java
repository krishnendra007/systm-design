class Jump {
    private final int start;
    private final int end;

    public Jump(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() { return start; }
    public int getEnd() { return end; }

    public boolean isSnake() {
        return start > end;
    }

    public boolean isLadder() {
        return start < end;
    }
}