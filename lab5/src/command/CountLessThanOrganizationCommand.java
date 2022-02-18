package command;

import main.CollectionOfWorkerManager;
import main.CommandReader;
import main.Messages;
import main.Messenger;

import java.io.IOException;

/**
 * class-command for counting elements of collection and variable Organization of this elements less than param
 */
public class CountLessThanOrganizationCommand implements Command{
    @Override
    public void execute(Messages messages, CommandReader reader, Messenger messenger,
                        String arg, CollectionOfWorkerManager collectionOfWorkerManager) throws IOException {
        collectionOfWorkerManager.countLessThanOrganization(reader.readOrganization());
    }
}
