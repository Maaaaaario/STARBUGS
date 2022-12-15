package guestmenu.dao;

import common.DAO;
import guestmenu.Drinks;

import java.sql.*;
import java.util.ArrayList;

public class DrinksDAOImpl extends DAO implements DrinksDAO {

    public DrinksDAOImpl() {
    }

    @Override
    public Drinks getDrinksById(String id) {
        Drinks Drinks = null;

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select * from Drinks where id = '" + id + "'";

            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int sales = rs.getInt("sales");
                String type = rs.getString("type");
                Drinks = new Drinks(id, name, price, sales, type);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Drinks;
    }

    @Override
    public ArrayList<Drinks> getAllDrinksByName(String DrinksName) {
        ArrayList<Drinks> DrinksList = new ArrayList<>();

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select * from Drinks where name like '" + DrinksName+"%'";

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
    public ArrayList<Drinks> getAllDrinksByType(String type) {
        ArrayList<Drinks> DrinksList = new ArrayList<>();

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select * from drinks where type like '%" + type+"%'";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("Id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int sales = rs.getInt("sales");
                Drinks Drinks = new Drinks(id, name, price, sales, type);
                DrinksList.add(Drinks);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DrinksList;
    }

    @Override
    public ArrayList<Drinks> getAllDrinks() {
        ArrayList<Drinks> DrinksList = new ArrayList<>();

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select * from Drinks";

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
    public ArrayList<String> getDrinksTypes() {
        ArrayList<String> DrinksTypes = new ArrayList<>();
        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select DISTINCT type from drinks";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String type = rs.getString("type");
                DrinksTypes.add(type);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DrinksTypes;
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

    @Override
    public void updateSales(String id, int number) {
        String sql = "update drinks set sales = sales + ? where id = ?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, number);
            ps.setString(2, id);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
