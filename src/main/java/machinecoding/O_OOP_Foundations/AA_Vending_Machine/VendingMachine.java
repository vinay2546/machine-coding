package machinecoding.O_OOP_Foundations.AA_Vending_Machine;

import java.util.List;
import java.util.Optional;

/**
 * Coordinates selection, payment, purchase, and cancellation for one customer.
 *
 * This implementation is intentionally in-memory and single-threaded.
 */
public class VendingMachine {
    private final Inventory inventory;
    private String selectedProductId;
    private int balanceInPaise;

    public VendingMachine(Inventory inventory) {
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory must not be null");
        }
        this.inventory = inventory;
    }

    public List<Product> getProducts() {
        return inventory.getProducts();
    }

    public Optional<Product> getProduct(String productId) {
        return inventory.getProduct(productId);
    }

    /**
     * Selects an available product. A failed selection leaves the current
     * selection and inserted balance unchanged. Switching products is allowed.
     */
    public SelectionResult selectProduct(String productId) {
        Optional<Product> product = inventory.getProduct(productId);
        if (product.isEmpty()) {
            return SelectionResult.PRODUCT_NOT_FOUND;
        }
        if (!inventory.isInStock(productId)) {
            return SelectionResult.OUT_OF_STOCK;
        }
        selectedProductId = productId;
        return SelectionResult.SELECTED;
    }

    /** Adds a positive amount in paise to the current transaction. */
    public void insertMoney(int amountInPaise) {
        if (amountInPaise <= 0) {
            throw new IllegalArgumentException("Inserted amount must be positive");
        }
        balanceInPaise = Math.addExact(balanceInPaise, amountInPaise);
    }

    public int getBalance() {
        return balanceInPaise;
    }

    /**
     * Attempts to complete the purchase. Failed attempts preserve the balance
     * and stock. A successful attempt decreases stock once and clears the
     * transaction after calculating change.
     */
    public PurchaseResult purchase() {
        if (selectedProductId == null) {
            return new PurchaseResult(
                    PurchaseStatus.NO_PRODUCT_SELECTED, null, 0, balanceInPaise);
        }

        Optional<Product> maybeProduct = inventory.getProduct(selectedProductId);
        if (maybeProduct.isEmpty() || !inventory.isInStock(selectedProductId)) {
            return new PurchaseResult(
                    PurchaseStatus.OUT_OF_STOCK, maybeProduct.orElse(null), 0, balanceInPaise);
        }

        Product product = maybeProduct.get();
        if (balanceInPaise < product.priceInPaise()) {
            return new PurchaseResult(
                    PurchaseStatus.INSUFFICIENT_FUNDS, product, 0, balanceInPaise);
        }

        int changeInPaise = balanceInPaise - product.priceInPaise();
        inventory.decreaseStock(product.id());
        selectedProductId = null;
        balanceInPaise = 0;

        return new PurchaseResult(PurchaseStatus.SUCCESS, product, changeInPaise, balanceInPaise);
    }

    /**
     * Cancels the current transaction. Calling this with no inserted money
     * and no selected product is a no-op and reports cancelled=false.
     */
    public TransactionResult cancelTransaction() {
        boolean hasTransaction = selectedProductId != null || balanceInPaise > 0;
        int refund = balanceInPaise;
        selectedProductId = null;
        balanceInPaise = 0;
        return new TransactionResult(hasTransaction, refund);
    }

    public List<InventoryItem> getInventory() {
        return inventory.getInventory();
    }
}
