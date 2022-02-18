package command;

import main.CollectionOfWorkerManager;
import main.CommandReader;
import main.Messages;
import main.Messenger;
import java.io.IOException;

/**
 * class-command for remove element of collection by id
 */
public class RemoveByIdCommand implements Command{
    public RemoveByIdCommand(){

    }
    @Override
    public void execute(Messages messages, CommandReader reader, Messenger messenger,
                        String arg, CollectionOfWorkerManager collectionOfWorkerManager) throws IOException {
        long id = Long.parseLong(arg);
        collectionOfWorkerManager.removeById(id);
        messenger.output("object was deleted");
    }
}
