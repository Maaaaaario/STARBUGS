package guestmenu.dao;

import common.UserType;
import common.dto.UserDto;
import guestmenu.Food;

import java.util.ArrayList;

public interface FoodDAO {
    Food getFoodById(String id);
    ArrayList<Food> getAllFoodByName(String foodName);
    ArrayList<Food> getAllFoodByType(String type);
    ArrayList<Food> getAllFood();

    ArrayList<String> getFoodTypes();

    void add(Food dto);

    void update(String id);

    void delete(String id);
}
