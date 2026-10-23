package machinecoding.O_OOP_Foundations.AA_Vending_Machine.states;

import machinecoding.O_OOP_Foundations.AA_Vending_Machine.interfaces.MachineState;
import machinecoding.O_OOP_Foundations.AA_Vending_Machine.inventory.Inventory;
import machinecoding.O_OOP_Foundations.AA_Vending_Machine.inventory.Product;
import machinecoding.O_OOP_Foundations.AA_Vending_Machine.vendingmachine.VendingMachine;

public class MoneyInsertedState implements MachineState {

    private VendingMachine vendingMachine;

    public MoneyInsertedState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void insertMoney(int amount) {
        int currentBalance = vendingMachine.getAmount() + amount;
        vendingMachine.setAmount(currentBalance);
    }

    @Override
    public void selectProducts(int productId) {
        Inventory inventory = vendingMachine.getInventory();
        Product product = inventory.getProducts().get(productId);

        if (!vendingMachine.hasSufficientAmount(product.getProductPrice())) {
            throw new IllegalArgumentException("amount available is not sufficient for this purchase");
        }

        if (!inventory.checkIfProductAvailable(productId)) {
            throw new IllegalArgumentException("Product is out of stock");
        }

        vendingMachine.setCurrentState(vendingMachine.getDispenseState());
    }


    @Override
    public void dispense(int productId) {
        throw new IllegalStateException("Product not chosen for dispense");
    }

    @Override
    public void cancel() {
        vendingMachine.setCurrentState(vendingMachine.getNoMoneyInsertedState());
        vendingMachine.setAmount(0);
        System.out.println("Transaction canceled returning the amount : " + vendingMachine.getAmount());
    }
}
