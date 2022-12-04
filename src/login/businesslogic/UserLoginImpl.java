package login.businesslogic;

import common.UserType;

import java.util.Scanner;

/**
 * @title: UserLoginImpl
 * @Author Qihang Yin
 * @Date: 2022/12/4 17:03
 * @Version 1.0
 */
public class UserLoginImpl implements UserLogin{

    private static Scanner keyboardInput;

    public void login() {
        System.out.println("Welcome to Starbugs Cafe!");

        keyboardInput = new Scanner(System.in);

        UserType userType = getLoginType();

        System.out.println();

        switch (userType) {
            case GUEST -> guestLogin();
        }



    }

    private void guestLogin() {
        System.out.println("Do you want to become registered?");





    }

    private String getYesOrNo() {
        System.out.print("[Y/N]: ");
        String input = keyboardInput.nextLine();
        return "";
    }

    private UserType getLoginType() {

        System.out.println("Please choose the type of user you'd like to login as.");
        System.out.println("1: guest (non-registered customer)");
        System.out.println("2: registered customer");
        System.out.println("3: admin");
        System.out.print("Your choise is: ");
        String input = keyboardInput.nextLine();

        if (input.length() != 1 || !Character.isDigit(input.charAt(0))
                || Integer.parseInt(input) < 1 || Integer.parseInt(input) > 3) {
            System.out.println("Please input 1 to 3.");
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
