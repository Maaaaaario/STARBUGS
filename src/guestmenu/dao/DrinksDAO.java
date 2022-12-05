package guestmenu.dao;

import guestmenu.Drinks;

import java.util.ArrayList;

public interface DrinksDAO {
    Drinks get(String id);
    ArrayList<Drinks> getAllDrinks();

    void add(Drinks drinks);

    void update(String id);

    void delete(String id);
}
