package command;

import main.CollectionOfWorkerManager;
import main.CommandReader;
import main.Messages;
import main.Messenger;

import java.io.IOException;

/**
 * class-command for adding worker in collection
 */
public class AddCommand implements Command{
    public AddCommand(){

    }

    @Override
    public void execute(Messages messages, CommandReader reader, Messenger messenger,
                        String arg, CollectionOfWorkerManager collectionOfWorkerManager) throws IOException {
        collectionOfWorkerManager.addWorker(reader.readWorker());
    }
}
