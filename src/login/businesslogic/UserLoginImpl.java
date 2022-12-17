package login.businesslogic;

import adminmenu.AdminSystem;
import adminmenu.AdminSystemImpl;
import common.CheckUtils;
import common.CommonUtils;
import common.UserType;
import common.dto.RegisterInfoDTO;
import common.dto.UserDTO;
import guestmenu.Product;
import guestmenu.ShoppingCart;
import login.dao.UserDAO;
import login.dao.UserDAOImpl;
import login.dao.UserLoginDAO;
import login.dao.UserLoginDAOImpl;

import java.util.ArrayList;
import java.util.Scanner;

import static guestmenu.CustomerMenu.*;

/**
 * @title: UserLoginImpl
 * @Author Qihang Yin
 * @Date: 2022/12/4 17:03
 * @Version 1.0
 */
public class UserLoginImpl implements UserLogin{

    private static Scanner keyboardInput;

    private static final String SEPARATER = "------------------------------";

    public void login() {
        while (true) {
            System.out.println(SEPARATER);
            System.out.println("Welcome to Starbugs Cafe!");
            System.out.println(SEPARATER);

            keyboardInput = new Scanner(System.in);

            UserType userType = getLoginType();

            System.out.println();

            switch (userType) {
                case GUEST -> guestLogin();
                case REGISTERED -> registeredCustomerLogin();
                case ADMIN -> adminLogin();
            }
        }
    }

    private void registeredCustomerLogin() {
        System.out.println(SEPARATER);
        System.out.println("Registered customer login");
        System.out.println(SEPARATER);

        boolean repeat;
        do {
            repeat = false;

            // input name
            System.out.println("Please enter your name.");
            System.out.print("Your name: ");
            String inputName = keyboardInput.nextLine().strip();

            // input password
            System.out.println("Please enter your password.");
            System.out.print("Your password: ");
            String inputPassword = keyboardInput.nextLine().strip();

            // get correct password
            UserLoginDAO userLoginDAO = new UserLoginDAOImpl();
            String correctPassword = userLoginDAO.getPassword(inputName, UserType.REGISTERED);

            if (correctPassword == null) {
                System.out.println("User doesn't exist.");

                // 1 to retry, 2 to go back
                String option = getOption();

                if ("1".equals(option)) {
                    repeat = true;
                    System.out.println();
                } else {
                    System.out.println();
                    return;
                }
            } else {
                if (correctPassword.equalsIgnoreCase(inputPassword)) {
                    System.out.println();
                    String id = userLoginDAO.getId(inputName, UserType.REGISTERED);
                    // go to registered customer menu
                    goToCustomerMenu(id);
                } else {
                    System.out.println("The password is incorrect.");

                    // 1 to retry, 2 to go back
                    String option = getOption();

                    if ("1".equals(option)) {
                        repeat = true;
                        System.out.println();
                    } else {
                        System.out.println();
                        return;
                    }
                }
            }
        } while (repeat);
    }

    private String getOption() {
        System.out.println("Enter 1 to retry, enter 2 to go back.");
        System.out.print("[1/2]: ");
        String yourChoice = keyboardInput.nextLine().strip();

        if (!CheckUtils.isValidChoice(yourChoice, 1, 2)) {
            System.out.println("Please enter 1 or 2.");
            System.out.println();
            return getOption();
        }

        return yourChoice;
    }

    private void adminLogin() {
        System.out.println(SEPARATER);
        System.out.println("Admin login");
        System.out.println(SEPARATER);

        boolean repeat;
        do {
            repeat = false;

            // input name
            System.out.println("Please enter your name.");
            System.out.print("Your name: ");
            String inputName = keyboardInput.nextLine().strip();

            // input password
            System.out.println("Please enter your password.");
            System.out.print("Your password: ");
            String inputPassword = keyboardInput.nextLine().strip();

            // get correct password
            UserLoginDAO userLoginDAO = new UserLoginDAOImpl();
            String correctPassword = userLoginDAO.getPassword(inputName, UserType.ADMIN);

            if (correctPassword == null) {
                System.out.println("User doesn't exist.");

                // 1 to retry, 2 to go back
                String option = getOption();

                if ("1".equals(option)) {
                    repeat = true;
                    System.out.println();
                } else {
                    System.out.println();
                    return;
                }
            } else {
                if (correctPassword.equalsIgnoreCase(inputPassword)) {
                    System.out.println();
                    //go to admin menu
                    AdminSystem adminSystem = new AdminSystemImpl();
                    adminSystem.startAdminMenu();
                } else {
                    System.out.println("The password is incorrect.");

                    // 1 to retry, 2 to go back
                    String option = getOption();

                    if ("1".equals(option)) {
                        repeat = true;
                        System.out.println();
                    } else {
                        System.out.println();
                        return;
                    }
                }
            }
        } while (repeat);
    }

    private void guestLogin() {
        System.out.println("Do you want to become registered?");
        String toRegister = CommonUtils.getYesOrNo(keyboardInput);

        if ("Y".equalsIgnoreCase(toRegister)) {
            // registration process
            String newId = register();
            // go to registered customer menu
            goToCustomerMenu(newId);
        } else {
            System.out.println();
            // go to unregistered customer menu
            goToGuestMenu();
        }
    }

    private String register() {
        System.out.println(SEPARATER);
        System.out.println("Welcome to registration!");
        System.out.println(SEPARATER);

        int nameCnt;
        String name;
        UserLoginDAO userLoginDAO = new UserLoginDAOImpl();
        do {
            // input name
            System.out.println("Please enter your name.");
            System.out.print("Your name: ");
            name = keyboardInput.nextLine().strip();

            // check if name already exist
            nameCnt = userLoginDAO.getNumberOfThisName(name);
            if (nameCnt > 0) {
                System.out.println("Username already exist.");
            }

        } while (nameCnt > 0);

        // input password
        System.out.println("Please enter your password.");
        System.out.print("Your password: ");
        String password = keyboardInput.nextLine().strip();

        // generate unique id
        String id = CommonUtils.generateUserId();

        // insert into table user
        UserDTO userDto = new UserDTO(id, name, password, UserType.REGISTERED);
        UserDAO userDAO = new UserDAOImpl();
        userDAO.add(userDto);

        // insert into table register_info
        RegisterInfoDTO registerInfoDto = new RegisterInfoDTO(id, 0, false, null);
        userLoginDAO.addRegisterInfo(registerInfoDto);

        System.out.println("Registered successfully. Your ID is: " + id);
        System.out.println();

        return id;
    }

    private UserType getLoginType() {

        System.out.println("Please choose the type of user you'd like to login as.");
        System.out.println("1: guest (non-registered customer)");
        System.out.println("2: registered customer");
        System.out.println("3: admin");
        System.out.print("Your choice is: ");
        String input = keyboardInput.nextLine().strip();

        if (!CheckUtils.isValidChoice(input, 1, 3)) {
            System.out.println("Please enter 1 to 3.");
            System.out.println();
            return getLoginType();
        }

        UserType userType = null;
        switch (input) {
            case "1" -> userType = UserType.GUEST;
            case "2" -> userType =  UserType.REGISTERED;
            case "3" -> userType =  UserType.ADMIN;
        }

        return userType;
    }
}
