package common;

import login.dao.UserLoginDAO;
import login.dao.UserLoginDAOImpl;

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
            formatInfo += "%-12s";
        }
        return formatInfo;
    }
}
