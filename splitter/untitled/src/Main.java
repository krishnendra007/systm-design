import java.util.List;

public class Main {
    public static void main(String[] args) {
        User u1 = new User("1", "A");
        User u2 = new User("2", "B");
        User u3 = new User("3", "C");

        List<Split> splits = List.of(
                new EqualSplit(u1),
                new EqualSplit(u2),
                new EqualSplit(u3)
        );

        Expense expense = new Expense(
                "e1", 300, u1,
                splits,
                new EqualSplitStrategy()
        );

        ExpenseService service = new ExpenseService();
        service.addExpense(expense);

        System.out.println(u1.balanceSheet.getBalances());
    }
}