import java.util.List;

class Expense {
    String id;
    double amount;
    User paidBy;
    List<Split> splits;
    SplitStrategy strategy;

    public Expense(String id, double amount, User paidBy,
                   List<Split> splits, SplitStrategy strategy) {
        this.id = id;
        this.amount = amount;
        this.paidBy = paidBy;
        this.splits = splits;
        this.strategy = strategy;

        strategy.validate(amount, splits);
        strategy.calculate(amount, splits);
    }
}