package repository;

import java.sql.SQLException;
import java.util.List;

public interface TableOperation {

    void insertTestData() throws SQLException, ClassNotFoundException;

    void getData();

    void updateUpdate();

    void creatTable() throws SQLException, ClassNotFoundException;

    void creatForeignKeys() throws SQLException, ClassNotFoundException;





}
