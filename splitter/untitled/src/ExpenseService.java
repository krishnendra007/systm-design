class ExpenseService {

    public void addExpense(Expense expense) {
        User paidBy = expense.paidBy;

        for (Split split : expense.splits) {
            User user = split.user;
            double amount = split.getAmount();

            if (user != paidBy) {
                paidBy.balanceSheet.updateBalance(user, amount);
                user.balanceSheet.updateBalance(paidBy, -amount);
            }
        }
    }
}