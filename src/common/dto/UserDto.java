package common.dto;

/**
 * @title: UserDto
 * @Author Qihang Yin
 * @Date: 2022/11/30 20:18
 * @Version 1.0
 */
public class UserDto {

    private int id;

    private String username;

    private String nickname;

    private String password;

    public UserDto(int id, String username, String nickname, String password) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }

    public int getId() {
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
}
