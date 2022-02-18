package command;

import main.CollectionOfWorkerManager;
import main.CommandReader;
import main.Messages;
import main.Messenger;

/**
 * class-command for getting info about collection
 */
public class InfoCommand implements Command{
    public InfoCommand (){

    }
    @Override
    public void execute(Messages messages, CommandReader reader, Messenger messenger,
                        String arg, CollectionOfWorkerManager collectionOfWorkerManager) {
        messenger.output(collectionOfWorkerManager.getInfo());
    }
}
