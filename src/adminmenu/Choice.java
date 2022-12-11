package adminmenu;

// This enum variable defines the choices user may make in the UI
public enum Choice {

    CHOICE1("1"),

    CHOICE2("2"),

    CHOICE3("3"),

    CHOICE4("4"),

    CHOICE5("5"),

    CHOICE6("6");

    String code;

    Choice(String code) {
        this.code = code;
    }

    public static Choice fromValue(String code) {
        if ("1".equals(code)) {
            return Choice.CHOICE1;
        } else if ("2".equals(code)) {
            return Choice.CHOICE2;
        } else if ("3".equals(code)) {
            return Choice.CHOICE3;
        } else if ("4".equals(code)) {
            return Choice.CHOICE4;
        } else if ("5".equals(code)) {
            return Choice.CHOICE5;
        } else if ("6".equals(code)) {
            return Choice.CHOICE6;
        } else {
            throw new IllegalArgumentException("no such choice");
        }
    }
}
