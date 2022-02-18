package command;

import main.CollectionOfWorkerManager;
import main.CommandReader;
import main.Messages;
import main.Messenger;

import java.io.IOException;

/**
 * class-command for getting help-massage
 */
public class HelpCommand implements Command {
    public HelpCommand(){
    }

    @Override
    public void execute(Messages messages, CommandReader reader, Messenger messenger,
                        String arg, CollectionOfWorkerManager collectionOfWorkerManager) throws IOException {
        messenger.output(messages.getCommands());
    }
}

