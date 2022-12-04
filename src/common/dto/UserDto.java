package common.dto;

import common.UserType;

/**
 * @title: UserDto
 * @Author Qihang Yin
 * @Date: 2022/11/30 20:18
 * @Version 1.0
 */
public class UserDto {

    private String id;

    private String username;

    private String nickname;

    private String password;

    private UserType type;

    public UserDto(String id, String username, String nickname, String password, UserType type) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.type = type;
    }



    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public UserType getType() {
        return type;
    }
}
