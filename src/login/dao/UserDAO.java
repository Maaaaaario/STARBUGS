package login.dao;

import common.UserType;
import common.dto.UserDto;

// dao demo
public interface UserDAO {

    UserDto get(String id);

    void add(UserDto dto);

    void update(String id, UserType type);

    void delete(String id);
}
