package guestmenu.dao;

import common.DAO;
import guestmenu.Food;

import java.sql.*;
import java.util.ArrayList;

public class FoodDAOImpl extends DAO implements FoodDAO {
    public FoodDAOImpl() {
    }

    @Override
    public Food getFoodById(String id) {
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
                int dailyInventory = rs.getInt("daily_inventory");
                food = new Food(id, name, price, type, sales, inventory,dailyInventory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return food;
    }

    @Override
    public ArrayList<Food> getAllFoodByName(String foodName) {
        ArrayList<Food> foodList = new ArrayList<>();

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select * from food where name like '" + foodName+"%'";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("Id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int sales = rs.getInt("sales");
                int inventory = rs.getInt("inventory");
                String type = rs.getString("type");
                int dailyInventory = rs.getInt("daily_inventory");
                Food food = new Food(id, name, price, type, sales, inventory,dailyInventory);
                foodList.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
    }

    @Override
    public ArrayList<Food> getAllFoodByType(String type) {
        ArrayList<Food> foodList = new ArrayList<>();

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select * from food where type like '%" + type+"%'";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("Id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int sales = rs.getInt("sales");
                int inventory = rs.getInt("inventory");
                int dailyInventory = rs.getInt("daily_inventory");
                Food food = new Food(id, name, price, type, sales, inventory,dailyInventory);
                foodList.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
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
                int dailyInventory = rs.getInt("daily_inventory");
                Food food = new Food(id, name, price, type, sales, inventory,dailyInventory);
                foodList.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
    }

    @Override
    public ArrayList<String> getFoodTypes() {
        ArrayList<String> foodTypes = new ArrayList<>();
        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select DISTINCT type from food";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String type = rs.getString("type");
                foodTypes.add(type);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodTypes;
    }

    @Override
    public void add(Food dto) {

    }

    @Override
    public void updateInventory(String id, int number) {
        String sql = "update food set inventory = inventory - ? where id = ?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, number);
            ps.setString(2, id);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void updateSales(String id, int number) {
        String sql = "update food set sales = sales + ? where id = ?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, number);
            ps.setString(2, id);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
