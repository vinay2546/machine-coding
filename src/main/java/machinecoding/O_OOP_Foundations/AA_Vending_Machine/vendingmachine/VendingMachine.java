package machinecoding.O_OOP_Foundations.AA_Vending_Machine.vendingmachine;

import machinecoding.O_OOP_Foundations.AA_Vending_Machine.interfaces.MachineState;
import machinecoding.O_OOP_Foundations.AA_Vending_Machine.inventory.Inventory;
import machinecoding.O_OOP_Foundations.AA_Vending_Machine.inventory.Product;
import machinecoding.O_OOP_Foundations.AA_Vending_Machine.states.DispenseState;
import machinecoding.O_OOP_Foundations.AA_Vending_Machine.states.MoneyInsertedState;
import machinecoding.O_OOP_Foundations.AA_Vending_Machine.states.NoMoneyInsertedState;

public class VendingMachine {

    private Inventory inventory;
    private int amount;

    private NoMoneyInsertedState noMoneyInsertedState;
    private MoneyInsertedState moneyInsertedState;
    private DispenseState dispenseState;
    private MachineState currentState;


    public VendingMachine(Inventory inventory) {
        this.noMoneyInsertedState = new NoMoneyInsertedState(this);
        this.moneyInsertedState = new MoneyInsertedState(this);
        this.dispenseState = new DispenseState(this);
        this.inventory = inventory;
        this.currentState = noMoneyInsertedState;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public MoneyInsertedState getMoneyInsertedState() {
        return moneyInsertedState;
    }

    public NoMoneyInsertedState getNoMoneyInsertedState() {
        return noMoneyInsertedState;
    }

    public DispenseState getDispenseState() { return dispenseState; }

    public void setCurrentState(MachineState currentState) {
        this.currentState = currentState;
    }

    public boolean hasSufficientAmount(int productAmount) {
        return amount >= productAmount;
    }

    public void insertMoney(int amount) {
        this.currentState.insertMoney(amount);
        System.out.println(amount + " amount inserted");
    }

    public void selectProduct(int productId) {
        this.currentState.selectProducts(productId);
        this.currentState.dispense(productId);
    }

    public void addProduct(Product product, int stock) {
        this.getInventory().addProduct(product, stock);
    }

    public void displayInventory() {
        for (Product product : inventory.getProducts().values()) {
            System.out.println(product.getProductName() + " -> " + inventory.getStocks().getOrDefault(product.getProductId(), 0));
        }
    }

    public void cancelTransaction() {
        currentState.cancel();
    }

}
