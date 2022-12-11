package guestmenu;

public class Food extends Product {
    private final String type;
    private int sales;
    private int inventory;

    private int dailyInventory;

    @Override
    public String toString() {
        String foodInform = "";
        foodInform += " Type: " + type;
        foodInform += " \tRemaining: " + sales;
        foodInform += " \tinventory: " + inventory;
        return foodInform;
    }

    public Food(String id, String name, double price, String type, int sales, int inventory, int dailyInventory) {
        super(id, name, price);
        this.type = type;
        this.sales = sales;
        this.inventory = inventory;
        this.dailyInventory = dailyInventory;
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

    public int getDailyInventory() {
        return dailyInventory;
    }

    public void setDailyInventory(int dailyInventory) {
        this.dailyInventory = dailyInventory;
    }
}
