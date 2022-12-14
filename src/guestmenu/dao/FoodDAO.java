package guestmenu.dao;

import guestmenu.Food;

import java.util.ArrayList;

public interface FoodDAO {
    Food getFoodById(String id);
    ArrayList<Food> getAllFoodByName(String foodName);
    ArrayList<Food> getAllFoodByType(String type);
    ArrayList<Food> getAllFood();

    ArrayList<String> getFoodTypes();

    void add(Food dto);

    void updateInventory(String id, int number);

    void delete(String id);

    void updateSales(String id,int number);


}
