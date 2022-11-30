package login.dao;

import common.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserLoginDAO extends DAO {


    public UserLoginDAO() throws ClassNotFoundException {
    }

    public String getPassword(String userName) {

        String password = "";

        try (Connection c = getConnection(); Statement s = c.createStatement()) {
  
            String sql = "select password from user where username = '" + userName + "'";
  
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