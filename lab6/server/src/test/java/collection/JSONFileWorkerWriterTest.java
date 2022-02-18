package collection;

import org.junit.jupiter.api.Test;
import worker.OrdinaryWorker;
import worker.Worker;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class JSONFileWorkerWriterTest {

    @Test
    void saveWorkers() {
        WorkerWriter workerWriter = new JSONFileWorkerWriter(System.getenv("DATA_FOR_LAB6"));
        Collection<Worker> collectionOfWorkers = new LinkedList<>();
        collectionOfWorkers.add(new OrdinaryWorker());
        collectionOfWorkers.add(new OrdinaryWorker());
        collectionOfWorkers.add(new OrdinaryWorker());
        workerWriter.saveWorkers(collectionOfWorkers);
    }
}