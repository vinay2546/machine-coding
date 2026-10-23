package machinecoding.O_OOP_Foundations.AA_Vending_Machine.states;

import machinecoding.O_OOP_Foundations.AA_Vending_Machine.interfaces.MachineState;
import machinecoding.O_OOP_Foundations.AA_Vending_Machine.vendingmachine.VendingMachine;

public class NoMoneyInsertedState implements MachineState {

    private VendingMachine vendingMachine;

    public NoMoneyInsertedState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void insertMoney(int amount) {
        vendingMachine.setAmount(amount);
        vendingMachine.setCurrentState(vendingMachine.getMoneyInsertedState());
    }


    @Override
    public void selectProducts(int productId) {
        throw new IllegalStateException("Cannot select product before inserting money");

    }

    @Override
    public void dispense(int productId) {
        throw new IllegalStateException("Cannot select product before inserting money");
    }

    @Override
    public void cancel() {
        System.out.println("Transaction cancelled moving machine to idle state");
        vendingMachine.setAmount(0);
        vendingMachine.setCurrentState(vendingMachine.getNoMoneyInsertedState());
    }
}
