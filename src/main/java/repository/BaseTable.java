package repository;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Сервисный родительский класс, куда вынесена реализация общих действий для всех таблиц
public class BaseTable implements Closeable {
    Connection connection;  // JDBC-соединение для работы с таблицей
    String tableName;       // Имя таблицы

    BaseTable(String tableName) throws SQLException, ClassNotFoundException { // Для реальной таблицы передадим в конструктор её имя
        this.tableName = tableName;
        this.connection = CoreBaseDB.getConnection(); // Установим соединение с СУБД для дальнейшей работы
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

    // Выполнить SQL команду без параметров в СУБД, по завершению выдать сообщение в консоль
    void executeSqlStatement(String sql, String description) throws SQLException, ClassNotFoundException {
        reopenConnection(); // переоткрываем (если оно неактивно) соединение с СУБД
        Statement statement = connection.createStatement();  // Создаем statement для выполнения sql-команд
        statement.execute(sql); // Выполняем statement - sql команду
        statement.close();      // Закрываем statement для фиксации изменений в СУБД
        if (description != null)
            System.out.println(description);
    }

    public double extractOneElementSql(String sql, String description) throws SQLException, ClassNotFoundException {
        reopenConnection(); // переоткрываем (если оно неактивно) соединение с СУБД
        PreparedStatement statement = connection.prepareStatement(sql);  // Создаем statement для выполнения sql-команд
        ResultSet resultSet = statement.executeQuery(); // Выполняем statement - sql команду
        double data = 0;

        while (resultSet.next()) {
            data = resultSet.getDouble(1);

        }


        if (description != null) {
            System.out.println(description);
        }

        resultSet.close();      // Закрываем statement для фиксации изменений в СУБД
        statement.close();      // Закрываем statement для фиксации изменений в СУБД

        return data;

    }

    public String extractOneStringElementSql(String sql, String description) throws SQLException, ClassNotFoundException {
        reopenConnection(); // переоткрываем (если оно неактивно) соединение с СУБД
        PreparedStatement statement = connection.prepareStatement(sql);  // Создаем statement для выполнения sql-команд
        ResultSet resultSet = statement.executeQuery(); // Выполняем statement - sql команду
        String data = null;

        while (resultSet.next()) {
            data = resultSet.getString(1);

        }


        if (description != null) {
            System.out.println(description);
        }

        resultSet.close();      // Закрываем statement для фиксации изменений в СУБД
        statement.close();      // Закрываем statement для фиксации изменений в СУБД

        return data;

    }


    public <T> List<T> extractSqlStatement(String sql, String description) throws SQLException, ClassNotFoundException {
        reopenConnection(); // переоткрываем (если оно неактивно) соединение с СУБД
        PreparedStatement statement = connection.prepareStatement(sql);  // Создаем statement для выполнения sql-команд
        ResultSet resultSet = statement.executeQuery(); // Выполняем statement - sql команду
        List<T> list = new ArrayList<T>();


        //if (resultSet.getObject(0) instanceof String) {
        while (resultSet.next()) {

            String data = resultSet.getString(1);
            list.add((T) data);
        }
//            System.out.println(list.toString());
//
        //}


        if (description != null) {
            System.out.println(description);
        }

        resultSet.close();      // Закрываем statement для фиксации изменений в СУБД
        statement.close();      // Закрываем statement для фиксации изменений в СУБД
        return list;
    }

    // Выполнить SQL команду без параметров в СУБД, по завершению выдать сообщение в консоль

    public void killAllData(String table_name) throws SQLException, ClassNotFoundException {
        executeSqlStatement("DELETE from " + table_name + "; "
                , "Очистка таблицы [" + table_name + "] ");
        executeSqlStatement("ALTER TABLE " + table_name + " AUTO_INCREMENT=0 "
                , "Обнуление инкремента [" + table_name + "] ");
    }

    public void killListData(String table_name, String name_column, List list) throws SQLException, ClassNotFoundException {
        for (Object elementList : list) {
            if (elementList instanceof String) {
                executeSqlStatement("DELETE from " + table_name + " WHERE " + name_column + " = '" + elementList + "';", null);
            } else {
                executeSqlStatement("DELETE from" + table_name + " WHERE " + name_column + " = " + elementList + ";", null);
            }
        }
    }

    void getSqlColumnDataArray(String sql, String description) throws SQLException, ClassNotFoundException {


//        for (int i=1;i<=rs.getMetaData().getColumnCount();i++) {
//            Object obj=rs.getObject(i); /
//            if(obj instanceof String) //if obj is a String
//                hMap.put(rs.getMetaData().getColumnName(i), new String(((String)obj).getBytes("UTF-8"),"UTF-8"));
//            else //for every other objects
//                hMap.put(rs.getMetaData().getColumnName(i), obj);


//        reopenConnection(); // переоткрываем (если оно неактивно) соединение с СУБД
//
//
//        Statement statement = connection.createStatement();  // Создаем statement для выполнения sql-команд
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM Products");
//        while (resultSet.next()) {
//
//            list.add(resultSet.getString());
//
//        }
//
//        statement.execute(sql); // Выполняем statement - sql команду
//
//
//        statement.close();      // Закрываем statement для фиксации изменений в СУБД
//        if (description != null)
//            System.out.println(description);
    }


    void executeSqlStatement(String sql) throws SQLException, ClassNotFoundException {
        executeSqlStatement(sql, null);
    }


    // Активизация соединения с СУБД, если оно не активно.
    void reopenConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            connection = CoreBaseDB.getConnection();
        }
    }
}
