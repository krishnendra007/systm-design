import java.util.List;

class EqualSplitStrategy implements SplitStrategy {

    public void validate(double amount, List<Split> splits) {
        if (splits == null || splits.isEmpty()) {
            throw new RuntimeException("Invalid splits");
        }
    }

    public void calculate(double amount, List<Split> splits) {
        double splitAmount = amount / splits.size();

        for (Split split : splits) {
            split.setAmount(splitAmount);
        }
    }
}