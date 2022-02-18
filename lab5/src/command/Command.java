package command;

import main.CollectionOfWorkerManager;
import main.CommandReader;
import main.Messages;
import main.Messenger;

import java.io.IOException;

/**
 * interface for commands
 */
public interface Command {
    void execute(Messages messages, CommandReader reader, Messenger messenger,
                        String arg, CollectionOfWorkerManager collectionOfWorkerManager) throws IOException;
}
