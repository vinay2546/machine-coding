package machinecoding.O_OOP_Foundations.AA_Vending_Machine.inventory;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Integer, Product> products;
    private Map<Integer, Integer> stocks;
    private int totalSlots;

    public Inventory(int totalSlots) {
        this.products = new HashMap<>();
        this.stocks = new HashMap<>();
        this.totalSlots = totalSlots;
    }

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public Map<Integer, Integer> getStocks() {
        return stocks;
    }

    public void addProduct(Product product, int stock) {

        if (totalSlots <= 0) {
            System.out.println("No slots available");
            return;
        }

        if (!products.containsKey(product.getProductId())){
            products.put(product.getProductId(), product);
            stocks.put(product.getProductId(), stock);
            totalSlots = totalSlots - 1;
            System.out.println("Product : " + product.getProductName() + " added. Current Stock " + stock);
        } else {
            System.out.println("Product already exists in the inventory try updating stock");
        }
    }

    public boolean checkIfProductAvailable(int productId) {
        return stocks.getOrDefault(productId, 0) > 0;
    }

    public void deductProductCount(int productId) {
        int stock = stocks.get(productId) - 1;
        if (stock == 0) {
            products.remove(productId);
            stocks.remove(productId);
            totalSlots = totalSlots + 1;
        } else {
            stocks.put(productId, stock);
        }



    }

}
