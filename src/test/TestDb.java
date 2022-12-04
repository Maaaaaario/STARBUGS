package test;

import common.UserType;
import common.dto.UserDto;
import login.dao.UserDAO;
import login.dao.UserDAOImpl;
import login.dao.UserLoginDAO;

/**
 * @title: TestDb
 * @Author Qihang Yin
 * @Date: 2022/11/30 20:34
 * @Version 1.0
 */
public class TestDb {
    public static void main(String[] args) {
        try {
//            UserLoginDAO userLoginDAO = new UserLoginDAO();
//
//            String s = userLoginDAO.getPassword("test");
//
//            System.out.println("The password is " + s);

            UserDAO userDAO = new UserDAOImpl();

//            UserDto dto = new UserDto("123", "Mario", "maaaaaario", "123456", UserType.GUEST);

//            userDAO.add(dto);

//            UserDto dto1 = userDAO.get("123");
//
//            System.out.println(dto1.getType().getCode());
//
//            userDAO.update("123", UserType.REGISTERED);
//
//            UserDto dto2 = userDAO.get("123");
//
//            System.out.println(dto2.getType().getCode());
//
            userDAO.delete("123");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
