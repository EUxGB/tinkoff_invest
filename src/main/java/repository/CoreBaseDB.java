package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CoreBaseDB  {


    // Блок объявления констант
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "root";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/tinkoff_invest " +
            "?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false "; //решился вопрос с переполнением name     ;serverTimezone=UTC
    public static Statement statement;
    public static Connection connection;
    static String DB_Driver = "com.mysql.cj.jdbc.Driver";

    // Получить новое соединение с БД
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DB_Driver);
        connection = DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);
        statement = connection.createStatement();
        System.out.println("connected");

        return connection;
    }
    // Закрытие
    public void close() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка закрытия SQL соединения!");
        }
    }



    public void insertTestData() throws SQLException, ClassNotFoundException {
        getConnection();
        //truncate table Table1


    }


    public void getData() {

    }


    public void updateUpdate() {

    }
}
