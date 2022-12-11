package adminmenu;
import adminmenu.dao.AdminDAO;
import adminmenu.dao.AdminDAOImpl;
import common.CheckUtils;
import common.CommonUtils;
import common.UserType;
import common.dto.InventoryDTO;
import common.dto.RegisterInfoDTO;
import common.dto.SalesDTO;
import common.dto.UserDTO;
import login.dao.UserDAO;
import login.dao.UserDAOImpl;
import login.dao.UserLoginDAO;
import login.dao.UserLoginDAOImpl;

import java.util.*;

public class AdminSystemImpl implements AdminSystem{

    private static final String SEPARATER = "------------------------------";
    static Scanner keyboardInput; // Define the scanner used to get user input
    
    // The method below is used to enable user make choices in UI, number of choices depends on number of functions involved
    public static Choice makeChoice (int numberOfChoices) {
        
        // Send reminder to the user, the remainder is sent according to number of choices involved in the current menu
        System.out.print ("\nYour choice is: ");
        String inputChoice = keyboardInput.nextLine().strip(); // Get input choice from user

        // If the user input is not in the valid range
        if (!CheckUtils.isValidChoice(inputChoice, 1, numberOfChoices)) {
            System.out.println("Invalid input, please try again"); // Send warning to the user
            return makeChoice (numberOfChoices); // The method calls itself to let the user do input again
        }
        
        // Give value to the enum variable according to what the user typed in
        return Choice.fromValue(inputChoice);
    }
    
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
    
    // The method below is used to get an integer number from user
    private int getIntFromKeyboard (int lowerBound, int upperBound) {
        String inputFromKeyboard = keyboardInput.nextLine().strip();
        int inputNumber;
        try {
            inputNumber =  Integer.parseInt(inputFromKeyboard); // If the input from user is integer number, just return it
        }
        catch (NumberFormatException ex) { // If the user input is invalid, catch the exception
            System.out.println ("Invalid input, please type in an integer."); // Send warning to the user
            return getIntFromKeyboard(lowerBound, upperBound); // The method calls itself recursively to ensure the user makes valid input
        }

        if (inputNumber < lowerBound || inputNumber > upperBound) {
            System.out.println("The number should not be less than " + lowerBound + " or greater than " + upperBound + " , please try again.");
            return getIntFromKeyboard(lowerBound, upperBound);
        }

        return inputNumber;
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
        Choice choice = makeChoice (3); // Call the pre-defined method to let the user make choice
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

        Choice choice = makeChoice (2);

        if (choice == Choice.CHOICE1) {
            System.out.println("Please enter product number.");
            int num = getIntFromKeyboard(1, list.size());

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
        Choice choice = makeChoice (2);
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

        Choice choice = makeChoice (2);

        if (choice == Choice.CHOICE1) {
            System.out.println("Please enter product number.");
            int num = getIntFromKeyboard(1, list.size());
            while (list.get(num-1).getDailyInventory() < 0) {
                System.out.println("Can't change the inventory of drinks, please try again.");
                num = getIntFromKeyboard(1, list.size());
            }

            System.out.println("Please enter the new daily inventory.");
            int newInventory = getIntFromKeyboard(0, Integer.MAX_VALUE);

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
        Choice choice = makeChoice (2);
        if (choice == Choice.CHOICE1) {
            System.out.println();
            checkSales();
        } else {
            System.out.println();
            adminMainMenu();
        }
    }
    
    private void reservationManagementMenu () {
        System.out.println("Welcome to reservation check menu, please choose what to do");
        System.out.println ("Choice 1: Check existing reservations");
        System.out.println ("Choice 2: Add new reservation");
        System.out.println ("Choice 3: Cancel existing reservation");
        System.out.println ("Choice 4: Go to admin main menu");
        Choice choice = makeChoice (4);
        if (choice == Choice.CHOICE4)
           adminMainMenu ();
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
        Choice choice = makeChoice(6);
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
