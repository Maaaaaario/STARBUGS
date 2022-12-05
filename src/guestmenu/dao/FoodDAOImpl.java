package guestmenu.dao;

import common.DAO;
import guestmenu.Food;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FoodDAOImpl extends DAO implements FoodDAO {
    public FoodDAOImpl() throws ClassNotFoundException {
    }

    @Override
    public Food get(String id) {
        Food food = null;

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select * from food where id = '" + id + "'";

            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int sales = rs.getInt("sales");
                int inventory = rs.getInt("inventory");
                String type = rs.getString("type");
                food = new Food(id, name, price, type, sales, inventory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return food;
    }

    @Override
    public ArrayList<Food> getAllFood() {
        ArrayList<Food> foodList = new ArrayList<>();

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select * from food";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("Id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int sales = rs.getInt("sales");
                int inventory = rs.getInt("inventory");
                String type = rs.getString("type");
                Food food = new Food(id, name, price, type, sales, inventory);
                foodList.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
    }

    @Override
    public void add(Food dto) {

    }

    @Override
    public void update(String id) {

    }

    @Override
    public void delete(String id) {

    }
}
