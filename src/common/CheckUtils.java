package common;

/**
 * @title: CheckUtils
 * @Author Qihang Yin
 * @Date: 2022/12/5 18:19
 * @Version 1.0
 */
public class CheckUtils {

    // check if the input is among the choices given
    public static boolean isValidChoice(String toCheck, int min, int max) {
        if (toCheck.length() != 1 || !Character.isDigit(toCheck.charAt(0))
                || Integer.parseInt(toCheck) < min || Integer.parseInt(toCheck) > max) {
            return false;
        }

        return true;
    }

    // check if the input is Y or N
    public static boolean isYOrN(String toCheck) {
        if (toCheck.length() == 1 && (toCheck.equalsIgnoreCase("Y") || toCheck.equalsIgnoreCase("N"))) {
            return true;
        }

        return false;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
