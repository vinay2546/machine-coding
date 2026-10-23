package machinecoding.O_OOP_Foundations.AA_Vending_Machine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Owns product definitions and stock quantities.
 *
 * The returned collections are snapshots so callers cannot mutate internal state.
 */
public class Inventory {
    private final Map<String, Product> productsById = new LinkedHashMap<>();
    private final Map<String, Integer> quantityByProductId = new LinkedHashMap<>();

    public void addProduct(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must not be negative");
        }
        if (productsById.containsKey(product.id())) {
            throw new IllegalArgumentException("Duplicate product id: " + product.id());
        }

        productsById.put(product.id(), product);
        quantityByProductId.put(product.id(), quantity);
    }

    public Optional<Product> getProduct(String productId) {
        return Optional.ofNullable(productsById.get(productId));
    }

    public int getQuantity(String productId) {
        return quantityByProductId.getOrDefault(productId, 0);
    }

    public boolean isInStock(String productId) {
        return getQuantity(productId) > 0;
    }

    public List<Product> getProducts() {
        return List.copyOf(productsById.values());
    }

    public List<InventoryItem> getInventory() {
        List<InventoryItem> snapshot = new ArrayList<>();
        for (Product product : productsById.values()) {
            snapshot.add(new InventoryItem(product, getQuantity(product.id())));
        }
        return List.copyOf(snapshot);
    }

    void decreaseStock(String productId) {
        Integer quantity = quantityByProductId.get(productId);
        if (quantity == null) {
            throw new IllegalArgumentException("Unknown product id: " + productId);
        }
        if (quantity == 0) {
            throw new IllegalStateException("Product is out of stock: " + productId);
        }
        quantityByProductId.put(productId, quantity - 1);
    }
}
