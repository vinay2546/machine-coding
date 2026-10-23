package machinecoding.O_OOP_Foundations.AA_Vending_Machine;

/** A read-only snapshot of a product and its current stock quantity. */
public record InventoryItem(Product product, int quantity) {
    public InventoryItem {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must not be negative");
        }
    }

    public boolean isInStock() {
        return quantity > 0;
    }
}
