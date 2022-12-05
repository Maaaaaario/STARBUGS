package guestmenu.dao;

import common.UserType;
import common.dto.UserDto;
import guestmenu.Food;

import java.util.ArrayList;

public interface FoodDAO {
    Food get(String id);
    ArrayList<Food> getAllFood();

    void add(Food dto);

    void update(String id);

    void delete(String id);
}
