import java.util.List;

class PercentageSplitStrategy implements SplitStrategy {

    public void validate(double amount, List<Split> splits) {
        double totalPercent = 0;
        for (Split split : splits) {
            totalPercent += ((PercentageSplit) split).percentage;
        }
        if (totalPercent != 100) {
            throw new RuntimeException("Percentages must sum to 100");
        }
    }

    public void calculate(double amount, List<Split> splits) {
        for (Split split : splits) {
            PercentageSplit ps = (PercentageSplit) split;
            split.setAmount(amount * ps.percentage / 100.0);
        }
    }
}