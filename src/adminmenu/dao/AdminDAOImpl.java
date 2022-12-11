package adminmenu.dao;

import common.DAO;
import common.dto.InventoryDTO;
import common.dto.SalesDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @title: AdminDAOImpl
 * @Author Qihang Yin
 * @Date: 2022/12/11 0:07
 * @Version 1.0
 */
public class AdminDAOImpl extends DAO implements AdminDAO{

    @Override
    public void deleteRegisterInfo(String id) {
        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "delete from register_info where id = '" + id + "'";

            s.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<InventoryDTO> getAllInventory() {

        List<InventoryDTO> list = new ArrayList<>();

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "SELECT name, price, inventory FROM food UNION ALL (SELECT name, price, -1 FROM drinks) ORDER BY name ASC";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int inventory = rs.getInt("inventory");
                list.add(new InventoryDTO(name, price, inventory));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void updatePrice(String name, double price) {
        String sql1 = "update food set price = ? where name = ?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql1)) {

            ps.setDouble(1, price);
            ps.setString(2, name);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql2 = "update drinks set price = ? where name = ?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql2)) {

            ps.setDouble(1, price);
            ps.setString(2, name);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SalesDTO> getAllSales() {

        List<SalesDTO> list = new ArrayList<>();

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "SELECT name, sales, daily_inventory FROM food UNION ALL (SELECT name, sales, -1 FROM drinks) ORDER BY name ASC";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                int sales = rs.getInt("sales");
                int dailyInventory = rs.getInt("daily_inventory");
                list.add(new SalesDTO(name, sales, dailyInventory));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void updateDailyInventory(String name, int inventory) {
        String sql = "update food set daily_inventory = ? where name = ?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, inventory);
            ps.setString(2, name);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshInventory() {
        String sql = "update food set inventory = daily_inventory";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
