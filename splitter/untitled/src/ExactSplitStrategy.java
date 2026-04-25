import java.util.List;

class ExactSplitStrategy implements SplitStrategy {

    public void validate(double amount, List<Split> splits) {
        double sum = 0;
        for (Split split : splits) {
            sum += split.getAmount();
        }
        if (Math.abs(sum - amount) > 0.001) {
            throw new RuntimeException("Exact amounts do not match total");
        }
    }

    public void calculate(double amount, List<Split> splits) {
        // Already provided
    }
}