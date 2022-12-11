package login.dao;

import common.UserType;
import common.dto.UserDTO;

// dao demo
public interface UserDAO {

    UserDTO get(String id);

    void add(UserDTO dto);

    void update(String id, UserType type);

    void delete(String id);
}
