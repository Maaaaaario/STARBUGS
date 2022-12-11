package common.dto;

/**
 * @title: SalesDTO
 * @Author Qihang Yin
 * @Date: 2022/12/11 1:57
 * @Version 1.0
 */
public class SalesDTO {

    private String name;

    private int sales;

    private int dailyInventory;

    public SalesDTO(String name, int sales, int dailyInventory) {
        this.name = name;
        this.sales = sales;
        this.dailyInventory = dailyInventory;
    }

    public String getName() {
        return name;
    }

    public int getSales() {
        return sales;
    }

    public int getDailyInventory() {
        return dailyInventory;
    }

    public void setDailyInventory(int dailyInventory) {
        this.dailyInventory = dailyInventory;
    }
}
