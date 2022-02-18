package data;

import user.Auth;
import exceptions.DBException;
import worker.Organization;
import worker.Worker;

import java.util.Collection;
import java.util.Set;

public interface DataReader {
    Collection<Worker> readElements() throws DBException;
    void getElement(long id) throws DBException;
    Set<Auth> readUsers() throws DBException;
    Worker getLastElement() throws DBException;
    Set<Organization> getOrganizations() throws DBException;
}
