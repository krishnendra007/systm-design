class DispenseState implements State {
    private VendingMachine machine;

    public DispenseState(VendingMachine machine) {
        this.machine = machine;
    }

    public void selectProduct(Product product) {
        System.out.println("⚠️ Processing...");
    }

    public void insertMoney(double amount) {
        System.out.println("⚠️ Already enough money inserted");
    }

    public void dispense() {
        Product product = machine.selectedProduct;

        machine.inventory.deduct(product);

        System.out.println("🎁 Dispensed: " + product.name);

        double change = machine.insertedMoney - product.price;
        if (change > 0) {
            System.out.println("💸 Change returned: " + change);
        }

        machine.reset();
        machine.setState(machine.idleState);
    }

    public void cancel() {
        System.out.println("⚠️ Cannot cancel now");
    }
}