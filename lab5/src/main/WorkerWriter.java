package main;

import main.worker.Worker;

import java.util.Collection;

/**
 * Interface of worker writer
 */
public interface WorkerWriter {
    /**
     * Save workers collection
     * @param workers workers collection
     */
    void saveWorkers(Collection<Worker> workers);
}
