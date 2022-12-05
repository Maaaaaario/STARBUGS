package guestmenu.dao;

import common.DAO;
import guestmenu.Drinks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DrinksDAOImpl extends DAO implements DrinksDAO {

    public DrinksDAOImpl() throws ClassNotFoundException {
    }

    @Override
    public Drinks get(String id) {
        return null;
    }

    @Override
    public ArrayList<Drinks> getAllDrinks() {
        ArrayList<Drinks> DrinksList = new ArrayList<>();

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select * from drinks";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("Id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int sales = rs.getInt("sales");
                String type = rs.getString("type");
                Drinks Drinks = new Drinks(id, name, price, sales, type);
                DrinksList.add(Drinks);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DrinksList;
    }

    @Override
    public void add(Drinks drinks) {

    }

    @Override
    public void update(String id) {

    }

    @Override
    public void delete(String id) {

    }
}
