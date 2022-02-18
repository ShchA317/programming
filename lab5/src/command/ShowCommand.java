package command;

import main.CollectionOfWorkerManager;
import main.CommandReader;
import main.Messages;
import main.Messenger;
import java.io.IOException;

/**
 * class-command for output collection
 */
public class ShowCommand implements Command{
    public void execute(Messages messages, CommandReader reader, Messenger messenger,
                        String arg, CollectionOfWorkerManager collectionOfWorkerManager) throws IOException {
        if (collectionOfWorkerManager.getSize() == 0) {
            messenger.output("Collection is empty");
        } else {
            for (int i = 0; i < collectionOfWorkerManager.getSize(); ++i) {
                messenger.output(collectionOfWorkerManager.getWorker(i));
            }
        }
    }
}
