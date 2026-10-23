package machinecoding.O_OOP_Foundations.AA_Vending_Machine;

import machinecoding.O_OOP_Foundations.AA_Vending_Machine.enums.Items;
import machinecoding.O_OOP_Foundations.AA_Vending_Machine.inventory.Inventory;
import machinecoding.O_OOP_Foundations.AA_Vending_Machine.inventory.Product;
import machinecoding.O_OOP_Foundations.AA_Vending_Machine.vendingmachine.VendingMachine;

/**
 * Placeholder for Vending Machine.
 * TODO: Implement the acceptance criteria in README.md.
 * Add tests for normal flows, edge cases, and invalid states.
 */
public class Solution {

    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine(new Inventory(4));
        vendingMachine.addProduct(new Product(Items.BISCUITS.getItemId(),Items.BISCUITS.getItemName(),10),10);
        vendingMachine.addProduct(new Product(Items.CAKE.getItemId(),Items.CAKE.getItemName(),50),2);
        vendingMachine.addProduct(new Product(Items.CHIPS.getItemId(),Items.CHIPS.getItemName(),20),1);
        vendingMachine.addProduct(new Product(Items.CHOC0LATE.getItemId(),Items.CHOC0LATE.getItemName(),30),5);
        vendingMachine.addProduct(new Product(Items.JUICE.getItemId(),Items.JUICE.getItemName(),40),4);

        vendingMachine.insertMoney(50);
//        vendingMachine.cancelTransaction();
        vendingMachine.selectProduct(Items.CAKE.getItemId());

        System.out.println("================");
        vendingMachine.displayInventory();
        System.out.println("================");

    }
}
