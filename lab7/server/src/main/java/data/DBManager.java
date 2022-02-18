package data;

import user.Auth;
import exceptions.DBException;
import worker.Worker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public class DBManager implements DataManager {
    private final DataWriter dataWriter;
    private final DataReader dataReader;

    public DBManager(String url, String user, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        this.dataReader = new DBReader(connection);
        this.dataWriter = new DBWriter(connection);
    }

    @Override
    public Collection<Worker> readCollection() throws DBException {
        return dataReader.readElements();
    }

    @Override
    public void addElement(Worker worker, Auth auth) throws DBException {
        dataWriter.addElement(worker, auth);
        dataReader.getLastElement();
    }

    @Override
    public void updateElement(Worker worker, long id, Auth auth) throws DBException {
        dataWriter.updateElement(worker, id, auth);
        dataReader.getElement(id);
    }


    @Override
    public void removeElement(long id, Auth auth) throws DBException {
        dataWriter.removeElement(id, auth);
    }

    @Override
    public void addUser(Auth auth) throws DBException {
        dataWriter.addUser(auth);
    }

    @Override
    public Set<Auth> readUsers() throws DBException {
        return dataReader.readUsers();
    }
}