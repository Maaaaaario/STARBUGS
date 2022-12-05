package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @title: Dao
 * @Author Qihang Yin
 * @Date: 2022/11/30 20:03
 * @Version 1.0
 */
public abstract class DAO {

    // cloud database
    private static final String url="jdbc:mysql://47.243.123.182:3306/cafe?useUnicode=true&useCharacter=utf8&useSSL=true";

    // local database
//    private static final String url="jdbc:mysql://localhost:3306/cafe?useUnicode=true&useCharacter=utf8&useSSL=true";
    private static final String user="root";
    private static final String pwd = "123456";

    public DAO() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,pwd);
    }
}
