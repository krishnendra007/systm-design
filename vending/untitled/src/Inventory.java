import java.util.*;

class Inventory {
    private Map<Product, Integer> stock = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        stock.put(product, stock.getOrDefault(product, 0) + quantity);
    }

    public boolean isAvailable(Product product) {
        return stock.getOrDefault(product, 0) > 0;
    }

    public void deduct(Product product) {
        stock.put(product, stock.get(product) - 1);
    }

    public void displayProducts() {
        for (Product p : stock.keySet()) {
            System.out.println(p.name + " | Price: " + p.price + " | Qty: " + stock.get(p));
        }
    }
}