package main;
import main.worker.*;

import java.util.LinkedList;

/**
 * this class is responsible for working with the collection
 */
public interface CollectionOfWorkerManager {
    Worker getWorker(int number);
    void addWorker(Worker worker);
    String getInfo();
    void updateWorkerById(long id, Worker worker);
    void removeById(long id);
    void clear();
    void save();
    Worker getHead();
    Worker removeHead();
    boolean addIfMin(Worker worker);
    long countLessThanOrganization(Organization organization);
    LinkedList getAscending();
    Double[] getFieldDescendingSalary();
    void parseDataToCollection();
    int getSize();
    void saveBeforeDangerActions();
    void recover();
}
