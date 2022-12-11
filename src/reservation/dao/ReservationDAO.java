package reservation.dao;

import common.UserType;
import common.dto.InventoryDTO;
import common.dto.ReservationDTO;

import java.util.Date;
import java.util.List;

public interface ReservationDAO {

    void addNewReservation(ReservationDTO dto);

    String getUserName(String id, UserType userType);

    int getExistingReservation(Date time);

    List<ReservationDTO> getAllReservation(String userId);

    List<ReservationDTO> getAllReservation();

    void deleteReservation(int id);
}
