package reservation;

import adminmenu.Choice;
import common.CommonUtils;
import common.UserType;
import common.dto.ReservationDTO;
import guestmenu.CustomerMenu;
import reservation.dao.ReservationDAO;
import reservation.dao.ReservationDAOImpl;

import java.util.*;

public class ReservationImpl implements Reservation{

    private static Scanner keyboard;

    private static final String SEPARATER = "------------------------------";

    public void reservationMenu(String userId) {

        keyboard = new Scanner(System.in);

        System.out.println(SEPARATER);
        System.out.println("Welcome to room reservation menu!");
        System.out.println(SEPARATER);
        System.out.println ("Please choose what to do.");
        System.out.println ("1: Add a new reservation");
        System.out.println ("2: View upcoming reservations");
        System.out.println ("3: Go back to customer main menu");
        Choice choice = CommonUtils.makeChoice(3, keyboard);
        System.out.println();
        switch (choice) {
            case CHOICE1 -> addReservation(userId);
            case CHOICE2 -> viewReservation(userId);
            default -> CustomerMenu.goToCustomerMenu(userId);
        }
    }

    private void viewReservation(String userId) {
        System.out.println(SEPARATER);
        System.out.println("Your reservation");
        System.out.println(SEPARATER);

        ReservationDAO reservationDAO = new ReservationDAOImpl();
        List<ReservationDTO> list = reservationDAO.getAllReservation(userId);

        if (list.size() == 0) {
            System.out.println("There is no upcoming reservation.");

        } else {
            String formatInfo = CommonUtils.printFormat(4);
            System.out.format(formatInfo, "No.", "Name", "People", "Time");
            System.out.println();
            for (int i = 0; i < list.size(); i++) {
                System.out.format(formatInfo, i + 1, list.get(i).getUserName(), list.get(i).getNumberOfPeople(), list.get(i).getTime().toString());
                System.out.println();
            }

            System.out.println("Please choose what to do.");
            System.out.println("1: Cancel reservation");
            System.out.println("2: Go back");

            Choice choice = CommonUtils.makeChoice(2, keyboard);

            if (choice == Choice.CHOICE1) {
                System.out.println("Please enter reservation number.");
                int num = CommonUtils.getIntFromKeyboard(1, list.size(), keyboard);

                reservationDAO.deleteReservation(list.get(num - 1).getId());

                System.out.println("reservation removed successfully.");

            }
        }

        System.out.println();
        reservationMenu(userId);
    }

    private void addReservation(String userId) {
        System.out.println(SEPARATER);
        System.out.println("New reservation");
        System.out.println(SEPARATER);

        Date time = CommonUtils.getDateInput(keyboard);

        ReservationDAO reservationDAO = new ReservationDAOImpl();
        String userName = reservationDAO.getUserName(userId, UserType.REGISTERED);

        System.out.println("Please enter the number of people who will be present.");
        int number = CommonUtils.getIntFromKeyboard(1, 20, keyboard);

        int cnt = reservationDAO.getExistingReservation(time);

        if (cnt == 0) {
            ReservationDTO reservationDTO = new ReservationDTO(userId, userName, number, time);
            reservationDAO.addNewReservation(reservationDTO);

            System.out.println("Your reservation has been saved.");
        } else {
            System.out.println("The room has already been booked at this time.");
        }

        System.out.println();
        reservationMenu(userId);
    }
}
