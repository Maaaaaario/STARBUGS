package guestmenu;

public class Food extends Produce{
    private final String type;
    private int sales;
    private int inventory;

    @Override
    public String toString() {
        String foodInform = "";
        foodInform += " Type: " + type;
        foodInform += " \tRemaining: " + sales;
        foodInform += " \tinventory: " + inventory;
        return foodInform;
    }

    public Food(String id, String name, double price, String type, int sales, int inventory) {
        super(id, name, price);
        this.type = type;
        this.sales = sales;
        this.inventory = inventory;
    }

    public String getType() {
        return type;
    }

    public int getSales() {
        return sales;
    }

    public int getInventory() {
        return inventory;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }
}
