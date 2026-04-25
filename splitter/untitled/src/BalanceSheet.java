import java.util.HashMap;
import java.util.Map;

class BalanceSheet {
    Map<User, Double> balances = new HashMap<>();

    public void updateBalance(User user, double amount) {
        balances.put(user, balances.getOrDefault(user, 0.0) + amount);
    }

    public Map<User, Double> getBalances() {
        return balances;
    }
}