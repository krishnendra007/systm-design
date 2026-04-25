class IdleState implements State {
    private VendingMachine machine;

    public IdleState(VendingMachine machine) {
        this.machine = machine;
    }

    public void selectProduct(Product product) {
        if (!machine.inventory.isAvailable(product)) {
            System.out.println("❌ Product out of stock");
            return;
        }
        machine.selectedProduct = product;
        System.out.println("✅ Selected: " + product.name);
        machine.setState(machine.selectionState);
    }

    public void insertMoney(double amount) {
        System.out.println("⚠️ Select product first");
    }

    public void dispense() {
        System.out.println("⚠️ No product selected");
    }

    public void cancel() {
        System.out.println("⚠️ Nothing to cancel");
    }
}