package data;

import user.Auth;
import exceptions.DBException;
import worker.Worker;

import java.util.Collection;
import java.util.Set;

public interface DataManager {
    Collection<Worker> readCollection() throws DBException;
    void addElement(Worker worker, Auth auth) throws DBException;

    void updateElement(Worker worker, long id, Auth auth) throws DBException;

    void removeElement(long id, Auth auth) throws DBException;

    void addUser(Auth auth) throws DBException;

    Set<Auth> readUsers() throws DBException;
}
