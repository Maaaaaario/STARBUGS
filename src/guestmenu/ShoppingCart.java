package guestmenu;

import adminmenu.Product;

import java.util.ArrayList;

public class ShoppingCart {
    private final Produce shoppingCartProduce;
    private int shoppingCartProduceNumber;

    public ShoppingCart(Produce shoppingCartProduce, int shoppingCartProduceNumber) {
        this.shoppingCartProduce = shoppingCartProduce;
        this.shoppingCartProduceNumber = shoppingCartProduceNumber;
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
    public void addShoppingCartProduce(Produce produce,int number) {

    }
}