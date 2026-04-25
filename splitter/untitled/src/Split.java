abstract class Split {
    User user;
    double amount;

    public Split(User user) {
        this.user = user;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}