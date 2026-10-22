package machinecoding.O_OOP_Foundations.AA_Vending_Machine;

/**
 * Small runnable demonstration of the vending-machine API.
 * Run this class to see a successful purchase and the returned change.
 */
public class Solution {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.addProduct(new Product("A1", "Water", 1500), 3);
        inventory.addProduct(new Product("B1", "Juice", 2500), 2);

        VendingMachine machine = new VendingMachine(inventory);
        System.out.println("Products: " + machine.getInventory());

        System.out.println("Selection: " + machine.selectProduct("A1"));
        machine.insertMoney(2000);
        PurchaseResult result = machine.purchase();

        System.out.println("Purchase: " + result.status());
        System.out.println("Dispensed: " + result.product().name());
        System.out.println("Change (paise): " + result.changeInPaise());
        System.out.println("Remaining stock: " + machine.getInventory());
    }
}
