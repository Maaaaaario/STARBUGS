package guestmenu;

public class Product {
    private final String id;
    private final String name;
    private final double price;

    @Override
    public String toString() {
        String produceInform = "";
        produceInform += " id: " + id;
        produceInform += " \tname: " + name;
        produceInform += " \tprice: " + price;
        return produceInform;
    }

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
