package data;

import user.Auth;
import exceptions.DBException;
import org.junit.jupiter.api.Test;
import worker.OrdinaryWorker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBWriterTest {

    final Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/workers", "postgres", "password");
    final DBWriter dbWriter = new DBWriter(connection);

    DBWriterTest() throws SQLException {

    }

    @Test
    void addElement() throws DBException {
        Auth auth = new Auth("postgres", "password");
        dbWriter.addElement(new OrdinaryWorker(), auth);
    }

    @Test
    void updateElement() {
    }

    @Test
    void clearElements() {
    }

    @Test
    void removeElement() {
    }

    @Test
    void addUser() {
    }
}