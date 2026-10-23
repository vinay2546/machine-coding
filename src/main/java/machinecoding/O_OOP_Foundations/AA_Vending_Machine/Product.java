package machinecoding.O_OOP_Foundations.AA_Vending_Machine;

/**
 * Immutable description of a product sold by the machine.
 * Prices are represented as integer paise.
 */
public record Product(String id, String name, int priceInPaise) {
    public Product {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Product id must not be blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name must not be blank");
        }
        if (priceInPaise <= 0) {
            throw new IllegalArgumentException("Product price must be positive");
        }
    }
}
