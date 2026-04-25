import java.util.ArrayList;
import java.util.List;

class Group {
    String id;
    List<User> users = new ArrayList<>();
    List<Expense> expenses = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }
}