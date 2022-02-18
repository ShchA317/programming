package command;

import main.CollectionOfWorkerManager;
import main.CommandReader;
import main.Messages;
import main.Messenger;

import java.io.IOException;

/**
 * class-command for exit without save
 */
public class ExitCommand implements Command{
    public ExitCommand(){

    }

    @Override
    public void execute(Messages messages, CommandReader reader, Messenger messenger,
                        String arg, CollectionOfWorkerManager collectionOfWorkerManager) throws IOException {
        reader.exit();
    }
}
