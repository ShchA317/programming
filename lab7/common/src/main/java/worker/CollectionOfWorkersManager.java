package worker;

import user.Auth;

import java.util.Collection;
import java.util.List;

public interface CollectionOfWorkersManager {
    void setCollection(Collection<Worker> collection);
    Worker getWorkerByNumber(int number);
    void addWorker(Worker worker, Auth auth);
    String getInfo();
    void updateWorkerById(long id, Worker worker, Auth auth);
    void removeById(long id, Auth auth);
    Worker getHead();
    Worker removeHead(Auth auth);
    void addIfMin(Worker worker, Auth auth);
    long countLessThanOrganization(Organization organization);
    List<Worker> getAscending();
    Double[] getFieldDescendingSalary();
    int getSize();
    void saveBeforeDangerActions();
    Collection<Worker> getListOfWorkers();
    boolean collectionIsEmpty();
}
