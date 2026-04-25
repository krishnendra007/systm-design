import java.util.List;

interface SplitStrategy {
    void validate(double amount, List<Split> splits);
    void calculate(double amount, List<Split> splits);
}