package machinecoding.O_OOP_Foundations.AA_Vending_Machine.enums;

public enum Items {
    JUICE(1000, "Juice"),
    CAKE(1001, "Cake"),
    TEA(1002, "Tea"),
    CHIPS(1003, "chips"),
    BISCUITS(1004, "Biscuits"),
    CHOC0LATE(1005, "Chocolate");

    private final int itemId;
    private final String itemName;

    Items(int itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }
}
