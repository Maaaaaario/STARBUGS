package login.dao;

import common.DAO;
import common.UserType;
import common.dto.RegisterInfoDto;
import common.dto.UserDto;

import java.sql.*;

/**
 * @title: UserLoginDAOImpl
 * @Author Qihang Yin
 * @Date: 2022/12/5 0:18
 * @Version 1.0
 */
public class UserLoginDAOImpl extends DAO implements UserLoginDAO{


    public UserLoginDAOImpl() {
    }

    @Override
    public void addRegisterInfo(RegisterInfoDto dto) {
        String sql = "insert into register_info values(?,?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, dto.getId());
            ps.setInt(2, dto.getStamps());
            ps.setInt(3, dto.getVipStatus() ? 1 : 0);
            ps.setDate(4, dto.getVipExpireDate() == null ? null : new Date(dto.getVipExpireDate().getTime()));

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getNumberOfThisName(String name) {
        int count = 0;

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select count(id) from user where name = '" + name + "'";

            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public String getMaxId() {
        String id = "";

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select max(id) from user";

            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                id = rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public String getPassword(String userName, UserType userType) {

        String password = null;

        try (Connection c = getConnection(); Statement s = c.createStatement()) {
  
            String sql = "select password from user where username = '" + userName + "'" + " and type = '" + userType.getCode() + "'";
  
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                password = rs.getString("password");
            }
  
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return password;
    }
}