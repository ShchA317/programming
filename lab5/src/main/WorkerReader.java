package main;

import main.worker.Worker;
import java.util.Collection;

/**
 * Interface for reading workers
 */
public interface WorkerReader {
    /**
     *
     * @return collection of objects of worker class
     */
    Collection<Worker> getWorkers();
}
