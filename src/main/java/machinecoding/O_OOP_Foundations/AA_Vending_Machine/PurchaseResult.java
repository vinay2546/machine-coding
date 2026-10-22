package machinecoding.O_OOP_Foundations.AA_Vending_Machine;

/**
 * Result of a purchase attempt.
 *
 * On failure, changeInPaise is zero and balanceInPaise reports the money
 * still held by the current transaction. Product is null when no product
 * was selected or the selected product is no longer available.
 */
public record PurchaseResult(
        PurchaseStatus status,
        Product product,
        int changeInPaise,
        int balanceInPaise) {

    public PurchaseResult {
        if (status == null) {
            throw new IllegalArgumentException("Status must not be null");
        }
        if (changeInPaise < 0 || balanceInPaise < 0) {
            throw new IllegalArgumentException("Money values must not be negative");
        }
    }

    public boolean isSuccessful() {
        return status == PurchaseStatus.SUCCESS;
    }
}
