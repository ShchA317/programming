package main;

/**
 * interface for producing commands
 */
public interface CommandFactory {
    void executeCommand(String command, CommandReader reader,
                        String args, CollectionOfWorkerManager collectionOfWorkerManager);
}
