package login.dao;

import common.UserType;
import common.dto.RegisterInfoDTO;

import java.util.Date;

public interface UserLoginDAO {

    String getMaxId();

    String getPassword(String userName, UserType userType);

    String getId(String userName, UserType userType);

    void addRegisterInfo(RegisterInfoDTO dto);

    int getNumberOfThisName(String name);

    RegisterInfoDTO getRegisterInfo(String id);

    void updateRegisterVipStatus(String id, Boolean status, Date date);

    void updateRegisterStamps(String id,int stamps);
}
