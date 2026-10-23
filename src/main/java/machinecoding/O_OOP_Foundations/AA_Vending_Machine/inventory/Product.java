package machinecoding.O_OOP_Foundations.AA_Vending_Machine.inventory;

public class Product {
    private int productId;
    private String productName;
    private int productPrice;

    public Product(int productId, String productName, int productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }


    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }
}
