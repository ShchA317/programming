package command;

import main.CollectionOfWorkerManager;
import main.CommandReader;
import main.Messages;
import main.Messenger;
import java.io.IOException;

/**
 * класс-команда для удаления первого элемента коллекции
 */
public class RemoveHeadCommand implements Command{
    @Override
    public void execute(Messages messages, CommandReader reader, Messenger messenger,
                        String arg, CollectionOfWorkerManager collectionOfWorkerManager) throws IOException {
        if(collectionOfWorkerManager.getSize()==0){
            messenger.output("collection is empty");
        }else {
            messenger.output(collectionOfWorkerManager.removeHead());
            messenger.output("Worker was removed");
        }
    }
}
