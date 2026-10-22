package machinecoding.O_OOP_Foundations.AA_Vending_Machine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineTest {
    private Inventory inventory;
    private VendingMachine machine;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        inventory.addProduct(new Product("A1", "Water", 1500), 2);
        inventory.addProduct(new Product("B1", "Juice", 2500), 1);
        machine = new VendingMachine(inventory);
    }

    @Test
    void successfulPurchaseReturnsChangeAndDecreasesStockOnce() {
        assertEquals(SelectionResult.SELECTED, machine.selectProduct("A1"));
        machine.insertMoney(2000);

        PurchaseResult result = machine.purchase();

        assertEquals(PurchaseStatus.SUCCESS, result.status());
        assertEquals("A1", result.product().id());
        assertEquals(500, result.changeInPaise());
        assertEquals(0, result.balanceInPaise());
        assertEquals(1, inventory.getQuantity("A1"));
        assertEquals(0, machine.getBalance());
    }

    @Test
    void exactPaymentProducesNoChange() {
        machine.selectProduct("A1");
        machine.insertMoney(1500);

        PurchaseResult result = machine.purchase();

        assertTrue(result.isSuccessful());
        assertEquals(0, result.changeInPaise());
        assertEquals(1, inventory.getQuantity("A1"));
    }

    @Test
    void insufficientFundsPreserveBalanceAndStock() {
        machine.selectProduct("A1");
        machine.insertMoney(1000);

        PurchaseResult result = machine.purchase();

        assertEquals(PurchaseStatus.INSUFFICIENT_FUNDS, result.status());
        assertEquals(1000, result.balanceInPaise());
        assertEquals(1000, machine.getBalance());
        assertEquals(2, inventory.getQuantity("A1"));
    }

    @Test
    void unknownProductDoesNotChangeSelectionOrBalance() {
        assertEquals(SelectionResult.SELECTED, machine.selectProduct("A1"));
        machine.insertMoney(500);

        assertEquals(SelectionResult.PRODUCT_NOT_FOUND, machine.selectProduct("missing"));
        assertEquals(500, machine.getBalance());

        PurchaseResult result = machine.purchase();
        assertEquals(PurchaseStatus.INSUFFICIENT_FUNDS, result.status());
        assertEquals(2, inventory.getQuantity("A1"));
    }

    @Test
    void soldOutProductCannotBeSelected() {
        inventory.addProduct(new Product("C1", "Soda", 1000), 0);

        assertEquals(SelectionResult.OUT_OF_STOCK, machine.selectProduct("C1"));
        assertEquals(0, inventory.getQuantity("C1"));
    }

    @Test
    void cancellationRefundsAllInsertedMoneyAndResetsTransaction() {
        machine.selectProduct("A1");
        machine.insertMoney(500);
        machine.insertMoney(1000);

        TransactionResult result = machine.cancelTransaction();

        assertTrue(result.cancelled());
        assertEquals(1500, result.refundedInPaise());
        assertEquals(0, machine.getBalance());
        assertFalse(machine.cancelTransaction().cancelled());
        assertEquals(2, inventory.getQuantity("A1"));
    }

    @Test
    void switchingProductsRetainsInsertedBalance() {
        machine.selectProduct("A1");
        machine.insertMoney(1000);

        assertEquals(SelectionResult.SELECTED, machine.selectProduct("B1"));
        machine.insertMoney(1500);
        PurchaseResult result = machine.purchase();

        assertEquals(PurchaseStatus.SUCCESS, result.status());
        assertEquals("B1", result.product().id());
        assertEquals(0, result.changeInPaise());
        assertEquals(2, inventory.getQuantity("A1"));
        assertEquals(0, inventory.getQuantity("B1"));
    }

    @Test
    void rejectsNonPositiveInsertedAmount() {
        assertThrows(IllegalArgumentException.class, () -> machine.insertMoney(0));
        assertThrows(IllegalArgumentException.class, () -> machine.insertMoney(-1));
        assertEquals(0, machine.getBalance());
    }

    @Test
    void purchaseWithoutSelectionDoesNotDiscardInsertedMoney() {
        machine.insertMoney(700);

        PurchaseResult result = machine.purchase();

        assertEquals(PurchaseStatus.NO_PRODUCT_SELECTED, result.status());
        assertEquals(700, result.balanceInPaise());
        assertEquals(700, machine.getBalance());
        assertEquals(2, inventory.getQuantity("A1"));
    }
}
