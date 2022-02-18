package command;

import main.CollectionOfWorkerManager;
import main.CommandReader;
import main.Messages;
import main.Messenger;
import java.io.IOException;

/**
 * class-command for get and remove first element of collection
 */
public class HeadCommand implements Command{
    public HeadCommand(){

    }

    @Override
    public void execute(Messages messages, CommandReader reader, Messenger messenger,
                        String arg, CollectionOfWorkerManager collectionOfWorkerManager) throws IOException {
       messenger.output(collectionOfWorkerManager.getHead());
    }
}
