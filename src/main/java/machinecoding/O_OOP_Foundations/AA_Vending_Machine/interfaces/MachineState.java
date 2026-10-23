package machinecoding.O_OOP_Foundations.AA_Vending_Machine.interfaces;

public interface MachineState {

    void insertMoney(int amount);

    void selectProducts(int productId);

    void dispense(int productId);

    void cancel();

}
