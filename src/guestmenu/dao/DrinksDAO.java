package guestmenu.dao;

import guestmenu.Drinks;

import java.util.ArrayList;

public interface DrinksDAO {
    Drinks getDrinksById(String id);
    ArrayList<Drinks> getAllDrinksByName(String drinksName);
    ArrayList<Drinks> getAllDrinksByType(String type);
    ArrayList<Drinks> getAllDrinks();

    ArrayList<String> getDrinksTypes();

    void add(Drinks drinks);

    void update(String id);

    void delete(String id);
    void updateSales(String id,int number);
}
