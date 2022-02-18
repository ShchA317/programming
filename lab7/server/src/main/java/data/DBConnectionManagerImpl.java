package data;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManagerImpl {
    public DBConnectionManagerImpl() {
    }

    public Connection connect(String url, String userName, String password) throws SQLException, ClassNotFoundException {
        DriverManager.registerDriver(new Driver());
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, userName, password);
    }
}
