package login.dao;

import common.DAO;
import common.UserType;
import common.dto.UserDto;

import java.sql.*;

/**
 * @title: UserDAOImpl
 * @Author Qihang Yin
 * @Date: 2022/12/5 0:18
 * @Version 1.0
 */
public class UserDAOImpl extends DAO implements UserDAO{

    public UserDAOImpl() {
    }

    @Override
    public UserDto get(String id) {

        UserDto dto = null;

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select * from user where id = '" + id + "'";

            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                String name = rs.getString("name");
                String password = rs.getString("password");
                UserType userType = UserType.fromValue(rs.getString("type"));
                dto = new UserDto(id, name, password, userType);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dto;
    }

    @Override
    public void add(UserDto dto) {
        String sql = "insert into user values(?,?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, dto.getId());
            ps.setString(2, dto.getName());
            ps.setString(3, dto.getPassword());
            ps.setString(4, dto.getType().getCode());

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "delete from user where id = '" + id + "'";

            s.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String id, UserType type) {
        String sql = "update user set type = ? where id = ?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, type.getCode());
            ps.setString(2, id);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
