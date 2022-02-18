package command;

import main.CollectionOfWorkerManager;
import main.CommandReader;
import main.Messages;
import main.Messenger;
import java.io.IOException;

/**
 * class-command for updating element by id
 */
public class UpdateCommand implements Command{
    @Override
    public void execute(Messages messages, CommandReader reader, Messenger messenger,
                        String arg, CollectionOfWorkerManager collectionOfWorkerManager) throws IOException {
        long id = Long.parseLong(arg);
        collectionOfWorkerManager.updateWorkerById(id, reader.readWorker());
    }
}
