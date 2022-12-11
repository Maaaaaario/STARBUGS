package guestmenu;

public class ShoppingCart {
    private final Product shoppingCartProduct;
    private int shoppingCartProductNumber;

    private String productType;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public ShoppingCart(Product shoppingCartProduct, int shoppingCartProductNumber, String productType) {
        this.shoppingCartProduct = shoppingCartProduct;
        this.shoppingCartProductNumber = shoppingCartProductNumber;
        this.productType = productType;
    }

    public Product getShoppingCartProduct() {
        return shoppingCartProduct;
    }

    public int getShoppingCartProductNumber() {
        return shoppingCartProductNumber;
    }

    public void setShoppingCartProductNumber(int shoppingCartProductNumber) {
        this.shoppingCartProductNumber = shoppingCartProductNumber;
    }
}
