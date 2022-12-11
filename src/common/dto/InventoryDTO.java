package common.dto;

/**
 * @title: InventoryDTO
 * @Author Qihang Yin
 * @Date: 2022/12/11 1:54
 * @Version 1.0
 */
public class InventoryDTO {

    private String name;

    private double price;

    private int inventory;

    public InventoryDTO(String name, double price, int inventory) {
        this.name = name;
        this.price = price;
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
