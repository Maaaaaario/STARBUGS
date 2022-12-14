package common.dto;

import common.UserType;

/**
 * @title: UserDTO
 * @Author Qihang Yin
 * @Date: 2022/11/30 20:18
 * @Version 1.0
 */
public class UserDTO {

    private String id;

    private String name;

    private String password;

    private UserType type;

    public UserDTO(String id, String name, String password, UserType type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.type = type;
    }



    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public UserType getType() {
        return type;
    }
}
