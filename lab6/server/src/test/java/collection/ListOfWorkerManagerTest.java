package collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import worker.CollectionOfWorkersManager;
import worker.Worker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListOfWorkerManagerTest {
    String dataFileName = "/home/alexander/HomeWorks/programming/Lab6/server/src/test/java/collection/testData.json";
    CollectionOfWorkersManager collectionOfWorkersManager =
            new ListOfWorkerManager(new JSONFileWorkerReader(dataFileName), new JSONFileWorkerWriter(dataFileName));
    @Test
    void getWorkerByNumber() {

    }

    @Test
    void addWorker() {
    }

    @Test
    void getInfo() {
        collectionOfWorkersManager.parseDataToCollection();
        System.out.println(collectionOfWorkersManager.getInfo());
    }

    @Test
    void updateWorkerById() {
    }

    @Test
    void removeById() {
    }

    @Test
    void clear() {
    }

    @Test
    void save() {
    }

    @Test
    void getHead() {
    }

    @Test
    void removeHead() {
    }

    @Test
    void addIfMin() {
    }

    @Test
    void countLessThanOrganization() {
    }

    @Test
    void getAscending() {
        List<Worker> ascendingListFromManager = collectionOfWorkersManager.getAscending();
        List<Worker> ascendingListFromStream = new ArrayList<>(collectionOfWorkersManager.getListOfWorkers());
        ascendingListFromStream.sort(Worker::compareTo);
        System.out.println(ascendingListFromManager.equals(ascendingListFromStream));
    }

    @Test
    void getFieldDescendingSalary() {
    }

    @Test
    void parseDataToCollection() {
        collectionOfWorkersManager.parseDataToCollection();
        Worker firstWorkerBefore = collectionOfWorkersManager.getHead();
        collectionOfWorkersManager.save();
        collectionOfWorkersManager.parseDataToCollection();
        Worker firstWorkerAfter = collectionOfWorkersManager.getHead();
        Assertions.assertEquals(firstWorkerAfter.toFormalString(), firstWorkerBefore.toFormalString());
    }

    @Test
    void getSize() {
    }

    @Test
    void saveBeforeDangerActions() {
    }

    @Test
    void recover() {
    }

    @Test
    void getListOfWorkers() {
    }

    @Test
    void getWorkerByID() {
    }
}