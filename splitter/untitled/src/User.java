class User {
    String id;
    String name;
    BalanceSheet balanceSheet;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.balanceSheet = new BalanceSheet();
    }
}