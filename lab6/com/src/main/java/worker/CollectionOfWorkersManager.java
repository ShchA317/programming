package worker;

import java.util.Collection;
import java.util.List;

public interface CollectionOfWorkersManager {
    Worker getWorkerByNumber(int number);
    void addWorker(Worker worker);
    String getInfo();
    void updateWorkerById(long id, Worker worker);
    void removeById(long id);
    void clear();
    void save();
    Worker getHead();
    Worker removeHead();
    void addIfMin(Worker worker);
    long countLessThanOrganization(Organization organization);
    List<Worker> getAscending();
    Double[] getFieldDescendingSalary();
    void parseDataToCollection();
    int getSize();
    void saveBeforeDangerActions();
    void recover();
    Collection<Worker> getListOfWorkers();
    boolean collectionIsEmpty();
}
