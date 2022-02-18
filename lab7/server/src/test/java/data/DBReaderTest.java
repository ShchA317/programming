package data;

import exceptions.DBException;
import org.junit.jupiter.api.Test;
import worker.DefaultWorker;
import worker.OrdinaryWorker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DBReaderTest {
    final Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/workers", "postgres", "password");
    final DataReader dataReader = new DBReader(connection);

    DBReaderTest() throws SQLException {

    }

    @Test
    void readElements() throws DBException {
        assertEquals(5, dataReader.readElements().size());
    }

    @Test
    void readUsers() {

    }

    @Test
    void getElement() {
    }

    @Test
    void getLastElement() throws DBException {
        DefaultWorker worker = new OrdinaryWorker();
        assertEquals(worker, dataReader.getLastElement());
    }
}