package login.dao;

import common.UserType;
import common.dto.RegisterInfoDto;

public interface UserLoginDAO {

    String getMaxId();

    String getPassword(String userName, UserType userType);

    void addRegisterInfo(RegisterInfoDto dto);

    int getNumberOfThisName(String name);
}
