package reservation.dao;

import common.DAO;
import common.UserType;
import common.dto.ReservationDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @title: ReservationDAOImpl
 * @Author Qihang Yin
 * @Date: 2022/12/11 21:25
 * @Version 1.0
 */
public class ReservationDAOImpl extends DAO implements ReservationDAO {

    @Override
    public void addNewReservation(ReservationDTO dto) {
        String sql = "insert into reservation (user_id, user_name, people, time) values (?,?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, dto.getUserId());
            ps.setString(2, dto.getUserName());
            ps.setInt(3, dto.getNumberOfPeople());
            ps.setTimestamp(4, new Timestamp(dto.getTime().getTime()));

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getUserName(String userId, UserType userType) {

        String name = null;

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select name from user where id = '" + userId + "'" + " and type = '" + userType.getCode() + "'";

            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                name = rs.getString("name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;
    }

    @Override
    public int getExistingReservation(java.util.Date time) {

        int cnt = 0;

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select id from reservation where time = '" + new Timestamp(time.getTime()) + "'";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                cnt ++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cnt;
    }

    @Override
    public List<ReservationDTO> getAllReservation(String userId) {

        List<ReservationDTO> list = new ArrayList<>();

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select * from reservation where time > now() and user_id = " + userId + " order by time asc";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("user_name");
                int people = rs.getInt("people");
                java.util.Date time = new java.util.Date(rs.getTimestamp("time").getTime());

                ReservationDTO reservationDTO = new ReservationDTO(userId, name, people, time);
                reservationDTO.setId(id);
                list.add(reservationDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<ReservationDTO> getAllReservation() {

        List<ReservationDTO> list = new ArrayList<>();

        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "select * from reservation where time > now() order by time asc";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String userId = rs.getString("user_id");
                String name = rs.getString("user_name");
                int people = rs.getInt("people");
                java.util.Date time = new java.util.Date(rs.getTimestamp("time").getTime());

                ReservationDTO reservationDTO = new ReservationDTO(userId, name, people, time);
                reservationDTO.setId(id);
                list.add(reservationDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void deleteReservation(int id) {
        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "delete from reservation where id = " + id;

            s.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReservationByUser(String userId) {
        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            String sql = "delete from reservation where user_id = '" + userId + "'";

            s.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
