package machinecoding.O_OOP_Foundations.AA_Vending_Machine.states;

import machinecoding.O_OOP_Foundations.AA_Vending_Machine.interfaces.MachineState;
import machinecoding.O_OOP_Foundations.AA_Vending_Machine.inventory.Inventory;
import machinecoding.O_OOP_Foundations.AA_Vending_Machine.inventory.Product;
import machinecoding.O_OOP_Foundations.AA_Vending_Machine.vendingmachine.VendingMachine;

public class DispenseState implements MachineState {

    private VendingMachine vendingMachine;

    public DispenseState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void insertMoney(int amount) {
        throw new IllegalStateException("Product is getting dispensed");
    }

    @Override
    public void selectProducts(int productId) {
        throw new IllegalStateException("Product is getting dispensed");
    }

    @Override
    public void dispense(int productId) {
        Inventory inventory = vendingMachine.getInventory();
        Product product = inventory.getProducts().get(productId);

        inventory.deductProductCount(productId);

        int change = vendingMachine.getAmount() - product.getProductPrice();

        vendingMachine.setAmount(0);
        vendingMachine.setCurrentState(vendingMachine.getNoMoneyInsertedState());
        System.out.println("Product : " + product.getProductName() + " dispensed. Change :" + change);
    }

    @Override
    public void cancel() {
        System.out.println("Cancellation not allowed at this stage");
    }
}
