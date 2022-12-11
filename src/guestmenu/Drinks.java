package guestmenu;

public class Drinks extends Product {
    private int sales;
    private final String type;

    public Drinks(String id, String name, double price, int sales, String type) {
        super(id, name, price);
        this.sales = sales;
        this.type = type;
    }

    public int getSales() {
        return sales;
    }

    public String getType() {
        return type;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }
}
