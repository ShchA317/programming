package command;

import main.CollectionOfWorkerManager;
import main.CommandReader;
import main.Messages;
import main.Messenger;
import java.io.IOException;

/**
 * class-command for save collection in file
 */
public class SaveCommand implements Command{
    @Override
    public void execute(Messages messages, CommandReader reader, Messenger messenger,
                        String arg, CollectionOfWorkerManager collectionOfWorkerManager) throws IOException {
        collectionOfWorkerManager.save();
        messenger.output("collection was saved");
    }
}
