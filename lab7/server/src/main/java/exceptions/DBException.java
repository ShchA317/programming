package exceptions;

import java.sql.SQLException;

public class DBException extends Throwable {
    public DBException(SQLException e) {
        super(e.getMessage());
    }
}
