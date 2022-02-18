package data;

import user.Auth;
import exceptions.DBException;
import worker.Worker;

public interface DataWriter {
    void addElement(Worker worker, Auth auth) throws DBException;
    void updateElement(Worker worker, long id, Auth auth) throws DBException;
    void removeElement(long id, Auth auth) throws DBException;
    void addUser(Auth auth) throws DBException;
}
