package test;

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
            UserLoginDAO userLoginDAO = new UserLoginDAO();

            String s = userLoginDAO.getPassword("test");

            System.out.println("The password is " + s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
