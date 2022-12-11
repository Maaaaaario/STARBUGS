package guestmenu;

import adminmenu.Product;

import java.util.ArrayList;

public class ShoppingCart {
    private final Produce shoppingCartProduce;
    private int shoppingCartProduceNumber;

    private String produceType;

    public String getProduceType() {
        return produceType;
    }

    public void setProduceType(String produceType) {
        this.produceType = produceType;
    }

    public ShoppingCart(Produce shoppingCartProduce, int shoppingCartProduceNumber, String produceType) {
        this.shoppingCartProduce = shoppingCartProduce;
        this.shoppingCartProduceNumber = shoppingCartProduceNumber;
        this.produceType = produceType;
    }

    public Produce getShoppingCartProduce() {
        return shoppingCartProduce;
    }

    public int getShoppingCartProduceNumber() {
        return shoppingCartProduceNumber;
    }

    public void setShoppingCartProduceNumber(int shoppingCartProduceNumber) {
        this.shoppingCartProduceNumber = shoppingCartProduceNumber;
    }
}
