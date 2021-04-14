package repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;

public class TestConnection {

    // Блок объявления констант
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "root";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/tinkoff_invest " +
            "?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false ";;
    public static Statement statement;
    public static Connection connection;
    static String DB_Driver = "com.mysql.cj.jdbc.Driver";






    // Получить новое соединение с БД
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DB_Driver);
        connection = DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);
        statement = connection.createStatement();
        System.out.println("connected");
        connection.close();
        System.out.println("disconnected");
        return connection;
    }





}
