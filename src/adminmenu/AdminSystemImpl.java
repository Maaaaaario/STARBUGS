package adminmenu;
import adminmenu.dao.AdminDAO;
import adminmenu.dao.AdminDAOImpl;
import common.CheckUtils;
import common.CommonUtils;
import common.UserType;
import common.dto.*;
import login.dao.UserDAO;
import login.dao.UserDAOImpl;
import login.dao.UserLoginDAO;
import login.dao.UserLoginDAOImpl;
import reservation.dao.ReservationDAO;
import reservation.dao.ReservationDAOImpl;

import java.util.*;

public class AdminSystemImpl implements AdminSystem{

    private static final String SEPARATER = "------------------------------";
    private static Scanner keyboardInput; // Define the scanner used to get user input

    
    // The method below is used get a float number from user
    private double getDoubleFromKeyboard() {
        String inputFromKeyboard = keyboardInput.nextLine().strip();
        double input;
        try {
            input =  Double.parseDouble(inputFromKeyboard); // If the input from user is float number, just return it
        }
        catch (NumberFormatException ex) { // If the user input is invalid, catch the exception
            System.out.println ("Invalid input, please type in a float number."); // Send warning to the user
            return getDoubleFromKeyboard(); // The method calls itself recursively to ensure the user makes valid input
        }

        if (input < 0) {
            System.out.println("The price can't be negative, please try again.");
            return getDoubleFromKeyboard();
        }

        return input;
    }
    
    // The method below is a menu used to let the user make choices
    private void customerManagementMenu() {
        System.out.println(SEPARATER);
        System.out.println("Welcome to customer management menu!");
        System.out.println(SEPARATER);
        System.out.println ("Please choose what to do.");
        System.out.println("1: Add new registration");
        System.out.println("2: Remove existing registration");
        System.out.println("3: Go to admin main menu");
        Choice choice = CommonUtils.makeChoice (3, keyboardInput); // Call the pre-defined method to let the user make choice
        if (choice == Choice.CHOICE1) { // The program reacts according to the choice made by the user
            addNewRegistration();
        } else if (choice == Choice.CHOICE2) {
            removeExistingRegistration();
        } else {
            adminMainMenu();
        }
    }

    private void removeExistingRegistration() {
        System.out.println(SEPARATER);
        System.out.println("Remove existing registration");
        System.out.println(SEPARATER);

        UserLoginDAO userLoginDAO = new UserLoginDAOImpl();

        // input name
        System.out.println("Please enter customer name to be removed.");
        System.out.print("Name: ");
        String name = keyboardInput.nextLine().strip();

        // get id
        String id = userLoginDAO.getId(name, UserType.REGISTERED);

        // customer not exist
        if (CheckUtils.isNullOrEmpty(id)) {
            System.out.println("Customer not exist.");

        } else { // customer exist
            UserDAO userDAO = new UserDAOImpl();
            userDAO.delete(id);

            AdminDAO adminDAO = new AdminDAOImpl();
            adminDAO.deleteRegisterInfo(id);

            ReservationDAO reservationDAO = new ReservationDAOImpl();
            reservationDAO.deleteReservationByUser(id);

            System.out.println("Customer ID: " + id + " is removed successfully.");
        }

        System.out.println();
        customerManagementMenu();
    }

    private void addNewRegistration() {
        System.out.println(SEPARATER);
        System.out.println("Add new registration");
        System.out.println(SEPARATER);

        int nameCnt;
        String name;
        UserLoginDAO userLoginDAO = new UserLoginDAOImpl();
        do {
            // input name
            System.out.println("Please enter customer name.");
            System.out.print("Name: ");
            name = keyboardInput.nextLine().strip();

            // check if name already exist
            nameCnt = userLoginDAO.getNumberOfThisName(name);
            if (nameCnt > 0) {
                System.out.println("Username already exist.");
            }

        } while (nameCnt > 0);

        // input password
        System.out.println("Please enter customer password.");
        System.out.print("Password: ");
        String password = keyboardInput.nextLine().strip();

        // get vip status from input
        System.out.println("Is this customer VIP or not?");
        String vipStatus = CommonUtils.getYesOrNo(keyboardInput);

        // generate unique id
        String id = CommonUtils.generateUserId();

        // insert into table user
        UserDTO userDto = new UserDTO(id, name, password, UserType.REGISTERED);
        UserDAO userDAO = new UserDAOImpl();
        userDAO.add(userDto);

        // insert into table register_info
        RegisterInfoDTO registerInfoDto;
        if ("Y".equalsIgnoreCase(vipStatus)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.YEAR, 1);
            registerInfoDto = new RegisterInfoDTO(id, 0, true, cal.getTime());
        } else {
            registerInfoDto = new RegisterInfoDTO(id, 0, false, null);
        }

        userLoginDAO.addRegisterInfo(registerInfoDto);

        System.out.println("Registered successfully. Customer ID is: " + id);
        System.out.println();

        customerManagementMenu();
    }

    // The method below is used to check the remaining number of a product according to the input ID number
    private void checkRemainingNumberOfProduct () {

        AdminDAO adminDAO = new AdminDAOImpl();
        List<InventoryDTO> list = adminDAO.getAllInventory();

        String formatInfo = CommonUtils.printFormat(4);
        System.out.format(formatInfo,"Num", "Name","Price","Inventory");
        System.out.println();
        for (int i = 0; i < list.size(); i ++) {
            System.out.format(formatInfo, i+1, list.get(i).getName(), list.get(i).getPrice(), list.get(i).getInventory() < 0 ? "/" : list.get(i).getInventory());
            System.out.println();
        }

        System.out.println ("Please choose what to do.");
        System.out.println("1: Edit price");
        System.out.println("2: Go back");

        Choice choice = CommonUtils.makeChoice (2, keyboardInput);

        if (choice == Choice.CHOICE1) {
            System.out.println("Please enter product number.");
            int num = CommonUtils.getIntFromKeyboard(1, list.size(), keyboardInput);

            System.out.println("Please enter the new price.");
            double newPrice = getDoubleFromKeyboard();

            adminDAO.updatePrice(list.get(num-1).getName(), newPrice);

            System.out.println("Price updated successfully.");
            System.out.println();
            inventoryManagementMenu();
        } else {
            System.out.println();
            inventoryManagementMenu();
        }
    }
    
    public void inventoryManagementMenu () {
        System.out.println(SEPARATER);
        System.out.println("Welcome to inventory management menu!");
        System.out.println(SEPARATER);
        System.out.println ("Please choose what to do.");
        System.out.println("1: Show all inventory and price");
//        System.out.println("2: Offer a discount");
        System.out.println("2: Go to admin main menu");
        Choice choice = CommonUtils.makeChoice (2, keyboardInput);
        if (choice == Choice.CHOICE1) {
            System.out.println();
            checkRemainingNumberOfProduct();
//        } else if (choice == Choice.CHOICE2) {
//            addDiscount();
        } else {
            System.out.println();
            adminMainMenu();
        }
    }
    
    private void checkSales () {

        AdminDAO adminDAO = new AdminDAOImpl();
        List<SalesDTO> list = adminDAO.getAllSales();

        String formatInfo = CommonUtils.printFormat(4);
        System.out.format(formatInfo,"Num", "Name","Sales","Daily Inventory");
        System.out.println();
        for (int i = 0; i < list.size(); i ++) {
            System.out.format(formatInfo, i+1, list.get(i).getName(), list.get(i).getSales(), list.get(i).getDailyInventory() < 0 ? "/" : list.get(i).getDailyInventory());
            System.out.println();
        }

        System.out.println ("Please choose what to do.");
        System.out.println("1: Adjust daily inventory");
        System.out.println("2: Go back");

        Choice choice = CommonUtils.makeChoice (2, keyboardInput);

        if (choice == Choice.CHOICE1) {
            System.out.println("Please enter product number.");
            int num = CommonUtils.getIntFromKeyboard(1, list.size(), keyboardInput);
            while (list.get(num-1).getDailyInventory() < 0) {
                System.out.println("Can't change the inventory of drinks, please try again.");
                num = CommonUtils.getIntFromKeyboard(1, list.size(), keyboardInput);
            }

            System.out.println("Please enter the new daily inventory.");
            int newInventory = CommonUtils.getIntFromKeyboard(0, Integer.MAX_VALUE, keyboardInput);

            adminDAO.updateDailyInventory(list.get(num-1).getName(), newInventory);

            System.out.println("Updated successfully.");
            System.out.println();
            salesManagementMenu();
        } else {
            System.out.println();
            salesManagementMenu();
        }
    }
    
//    public static boolean checkIfIdContainsOnlyNumbers (String id) {
//        for (int i = 0; i < id.length(); i ++) {
//            if (!Character.isDigit(id.charAt(i)))
//                return false;
//        }
//        return true;
//    }
    
    private void salesManagementMenu () {
        System.out.println(SEPARATER);
        System.out.println("Welcome to sales management menu!");
        System.out.println(SEPARATER);
        System.out.println ("Please choose what to do.");
        System.out.println("1: Show all annual sales and daily inventory");
        System.out.println("2: Go to admin main menu");
        Choice choice = CommonUtils.makeChoice (2, keyboardInput);
        if (choice == Choice.CHOICE1) {
            System.out.println();
            checkSales();
        } else {
            System.out.println();
            adminMainMenu();
        }
    }
    
    private void reservationManagementMenu () {
        System.out.println(SEPARATER);
        System.out.println("Welcome to reservation management menu!");
        System.out.println(SEPARATER);
        System.out.println("Please choose what to do.");
        System.out.println ("1: Check upcoming reservations");
        System.out.println ("2: Add new reservation");
        System.out.println ("3: Go to admin main menu");
        Choice choice = CommonUtils.makeChoice (4, keyboardInput);
        System.out.println();
        switch (choice) {
            case CHOICE1 -> viewReservation();
            case CHOICE2 -> addReservation();
            default -> adminMainMenu();
        }
    }

    private void viewReservation() {
        System.out.println(SEPARATER);
        System.out.println("Upcoming reservations");
        System.out.println(SEPARATER);

        ReservationDAO reservationDAO = new ReservationDAOImpl();
        List<ReservationDTO> list = reservationDAO.getAllReservation();

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

            Choice choice = CommonUtils.makeChoice(2, keyboardInput);

            if (choice == Choice.CHOICE1) {
                System.out.println("Please enter reservation number.");
                int num = CommonUtils.getIntFromKeyboard(1, list.size(), keyboardInput);

                reservationDAO.deleteReservation(list.get(num - 1).getId());

                System.out.println("reservation removed successfully.");

            }
        }

        System.out.println();
        reservationManagementMenu();
    }

    private void addReservation() {
        System.out.println(SEPARATER);
        System.out.println("New reservation");
        System.out.println(SEPARATER);

        // input name
        System.out.println("Please enter customer name.");
        System.out.print("Name: ");
        String name = keyboardInput.nextLine().strip();

        // get id
        UserLoginDAO userLoginDAO = new UserLoginDAOImpl();
        String id = userLoginDAO.getId(name, UserType.REGISTERED);

        // customer not exist
        if (CheckUtils.isNullOrEmpty(id)) {
            System.out.println("Customer not exist.");

        } else {
            Date time = CommonUtils.getDateInput(keyboardInput);

            ReservationDAO reservationDAO = new ReservationDAOImpl();

            System.out.println("Please enter the number of people who will be present.");
            int number = CommonUtils.getIntFromKeyboard(1, 20, keyboardInput);

            int cnt = reservationDAO.getExistingReservation(time);

            if (cnt == 0) {
                ReservationDTO reservationDTO = new ReservationDTO(id, name, number, time);
                reservationDAO.addNewReservation(reservationDTO);

                System.out.println("Your reservation has been saved.");
            } else {
                System.out.println("The room has already been booked at this time.");
            }
        }

        System.out.println();
        reservationManagementMenu();
    }
    
    // This is the main menu of the administration page
    private void adminMainMenu () {
        System.out.println(SEPARATER);
        System.out.println("Welcome to admin main menu!");
        System.out.println(SEPARATER);
        System.out.println ("Please choose what to do.");
        System.out.println ("1: Go to customer management menu");
        System.out.println ("2: Go to inventory management menu");
        System.out.println ("3: Go to sales management menu");
        System.out.println ("4: Go to reservation management menu");
        System.out.println ("5: Refresh inventory");
        System.out.println ("6: Exit the system");
        Choice choice = CommonUtils.makeChoice (6, keyboardInput);
        System.out.println();
        switch (choice) {
            case CHOICE1 -> customerManagementMenu();
            case CHOICE2 -> inventoryManagementMenu();
            case CHOICE3 -> salesManagementMenu();
            case CHOICE4 -> reservationManagementMenu();
            case CHOICE5 -> refreshInventory();
            default -> {}
        }
    }

    private void refreshInventory() {
        AdminDAO adminDAO = new AdminDAOImpl();
        adminDAO.refreshInventory();

        System.out.println("The inventory is successfully refreshed.");
        System.out.println();
        adminMainMenu();
    }

    public void startAdminMenu() {
        keyboardInput = new Scanner (System.in); 
        adminMainMenu ();
    }
}
