public class Main {
    public static void main(String[] args) {

        VendingMachine vm = new VendingMachine();

        Product coke = new Product("Coke", 20);
        Product chips = new Product("Chips", 10);

        vm.inventory.addProduct(coke, 2);
        vm.inventory.addProduct(chips, 1);

        vm.inventory.displayProducts();

        System.out.println("\n--- Scenario 1 ---");
        vm.selectProduct(coke);
        vm.insertMoney(10);
        vm.insertMoney(10);
        vm.dispense();

        System.out.println("\n--- Scenario 2 ---");
        vm.selectProduct(chips);
        vm.insertMoney(5);
        vm.cancel();

        System.out.println("\n--- Scenario 3 (Out of stock) ---");
        vm.selectProduct(chips);
        vm.insertMoney(10);
        vm.dispense();

        vm.selectProduct(chips); // should fail
    }
}