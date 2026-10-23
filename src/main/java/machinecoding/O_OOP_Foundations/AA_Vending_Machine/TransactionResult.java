package machinecoding.O_OOP_Foundations.AA_Vending_Machine;

/** Result of cancelling the current transaction. */
public record TransactionResult(boolean cancelled, int refundedInPaise) {
    public TransactionResult {
        if (refundedInPaise < 0) {
            throw new IllegalArgumentException("Refund must not be negative");
        }
        if (!cancelled && refundedInPaise != 0) {
            throw new IllegalArgumentException("A non-cancelled transaction cannot refund money");
        }
    }
}
