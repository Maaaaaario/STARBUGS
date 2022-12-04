package controller;

import login.businesslogic.UserLogin;
import login.businesslogic.UserLoginImpl;

/**
 * @title: Start
 * @Author Qihang Yin
 * @Date: 2022/11/21 22:52
 * @Version 1.0
 */
public class Start {

    public static void main(String[] args) {

        UserLogin userLogin = new UserLoginImpl();
        userLogin.login();

    }
}
