class VendingMachine {

    State idleState;
    State selectionState;
    State dispenseState;

    State currentState;

    Inventory inventory;

    Product selectedProduct;
    double insertedMoney;

    public VendingMachine() {
        inventory = new Inventory();

        idleState = new IdleState(this);
        selectionState = new SelectionState(this);
        dispenseState = new DispenseState(this);

        currentState = idleState;
    }

    public void setState(State state) {
        this.currentState = state;
    }

    // Public APIs
    public void selectProduct(Product product) {
        currentState.selectProduct(product);
    }

    public void insertMoney(double amount) {
        currentState.insertMoney(amount);
    }

    public void dispense() {
        currentState.dispense();
    }

    public void cancel() {
        currentState.cancel();
    }

    public void reset() {
        selectedProduct = null;
        insertedMoney = 0;
    }
}