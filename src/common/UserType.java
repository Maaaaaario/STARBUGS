package common;

public enum UserType {
    GUEST("G"),
    REGISTERED("R"),
    ADMIN("A");

    private String code;

    UserType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static UserType fromValue(String code) {
        if ("G".equals(code)) {
            return GUEST;
        } else if ("R".equals(code)) {
            return REGISTERED;
        } else if ("A".equals(code)) {
            return ADMIN;
        } else {
            throw new IllegalArgumentException("No such user type.");
        }
    }
}
