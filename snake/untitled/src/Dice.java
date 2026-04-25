import java.util.Random;

class Dice {
    private final int min;
    private final int max;
    private final Random random;

    public Dice(int min, int max) {
        this.min = min;
        this.max = max;
        this.random = new Random();
    }

    public int roll() {
        return random.nextInt(max - min + 1) + min;
    }
}