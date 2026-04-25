class SelectionState implements State {
    private VendingMachine machine;

    public SelectionState(VendingMachine machine) {
        this.machine = machine;
    }

    public void selectProduct(Product product) {
        System.out.println("⚠️ Product already selected");
    }

    public void insertMoney(double amount) {
        machine.insertedMoney += amount;
        System.out.println("💰 Inserted: " + amount + " | Total: " + machine.insertedMoney);

        if (machine.insertedMoney >= machine.selectedProduct.price) {
            machine.setState(machine.dispenseState);
        }
    }

    public void dispense() {
        System.out.println("⚠️ Insert money first");
    }

    public void cancel() {
        System.out.println("🔄 Transaction cancelled. Refunding: " + machine.insertedMoney);
        machine.reset();
        machine.setState(machine.idleState);
    }
}