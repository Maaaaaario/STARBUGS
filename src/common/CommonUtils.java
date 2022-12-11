package common;

import adminmenu.Choice;
import login.dao.UserLoginDAO;
import login.dao.UserLoginDAOImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @title: CommonUtils
 * @Author Qihang Yin
 * @Date: 2022/12/10 22:46
 * @Version 1.0
 */
public class CommonUtils {

    // id should be like 00000001
    public static String generateUserId() {

        String newId;

        UserLoginDAO userLoginDAO = new UserLoginDAOImpl();
        String maxId = userLoginDAO.getMaxId();

        if (CheckUtils.isNullOrEmpty(maxId)) {
            newId = "00000001";
        } else {
            newId = String.format("%08d", Integer.parseInt(maxId) + 1);
        }

        return newId;
    }

    // get user input Y or N
    public static String getYesOrNo(Scanner keyboardInput) {
        System.out.print("[Y/N]: ");
        String input = keyboardInput.nextLine().strip();
        if (!CheckUtils.isYOrN(input)) {
            System.out.println("Please enter Y/N.");
            return getYesOrNo(keyboardInput);
        }
        return input;
    }

    public static String printFormat(int number) {
        String formatInfo = "";
        for(int i=0;i<number;i++) {
            formatInfo += "%-16s";
        }
        return formatInfo;
    }

    // The method below is used to enable user make choices in UI, number of choices depends on number of functions involved
    public static Choice makeChoice (int numberOfChoices, Scanner keyboardInput) {

        // Send reminder to the user, the remainder is sent according to number of choices involved in the current menu
        System.out.print ("\nYour choice is: ");
        String inputChoice = keyboardInput.nextLine().strip(); // Get input choice from user

        // If the user input is not in the valid range
        if (!CheckUtils.isValidChoice(inputChoice, 1, numberOfChoices)) {
            System.out.println("Invalid input, please try again"); // Send warning to the user
            return makeChoice (numberOfChoices, keyboardInput); // The method calls itself to let the user do input again
        }

        // Give value to the enum variable according to what the user typed in
        return Choice.fromValue(inputChoice);
    }

    public static Date getDateInput(Scanner keyboard) {

        System.out.println("Please enter the date you want for reservation(yyyy-MM-dd).");
        String inputDate = keyboard.nextLine().strip();

        System.out.println("Please enter the o'clock you want for reserve(HH).");
        String inputHour = keyboard.nextLine().strip();

        String timeToParse = inputDate + " " + inputHour + ":00:00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setLenient(false);
        Date newTime;
        try {
            newTime = format.parse(timeToParse);
        } catch (ParseException e) {
            System.out.println("Illegal datetime, please try again.");
            return getDateInput(keyboard);
        }

        if (newTime.before(new Date())) {
            System.out.println("Past time cannot be reserved, please try again.");
            return getDateInput(keyboard);
        }

        return newTime;

    }

    // The method below is used to get an integer number from user
    public static int getIntFromKeyboard (int lowerBound, int upperBound, Scanner keyboardInput) {
        String inputFromKeyboard = keyboardInput.nextLine().strip();
        int inputNumber;
        try {
            inputNumber =  Integer.parseInt(inputFromKeyboard); // If the input from user is integer number, just return it
        }
        catch (NumberFormatException ex) { // If the user input is invalid, catch the exception
            System.out.println ("Invalid input, please type in an integer."); // Send warning to the user
            return getIntFromKeyboard(lowerBound, upperBound, keyboardInput); // The method calls itself recursively to ensure the user makes valid input
        }

        if (inputNumber < lowerBound || inputNumber > upperBound) {
            System.out.println("The number should not be less than " + lowerBound + " or greater than " + upperBound + " , please try again.");
            return getIntFromKeyboard(lowerBound, upperBound, keyboardInput);
        }

        return inputNumber;
    }

}
