package login.dao;

import common.UserType;
import common.dto.RegisterInfoDto;

public interface UserLoginDAO {

    String getMaxId();

    String getPassword(String userName, UserType userType);

    void addRegisterInfo(RegisterInfoDto dto);

    int getNumberOfThisName(String name);

    RegisterInfoDto getRegisterInfo(String id);

    void updateRegisterVipStatus(String id,Boolean status);

    void updateRegisterStamps(String id,int stamps);
}
