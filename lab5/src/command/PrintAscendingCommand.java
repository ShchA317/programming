package command;

import main.CollectionOfWorkerManager;
import main.CommandReader;
import main.Messages;
import main.Messenger;
import main.worker.Worker;

import java.io.IOException;
import java.util.LinkedList;

/**
 * class-command for output ascending list of workers
 */
public class PrintAscendingCommand implements Command{

    @Override
    public void execute(Messages messages, CommandReader reader, Messenger messenger,
                        String arg, CollectionOfWorkerManager collectionOfWorkerManager) throws IOException {
        LinkedList<Worker> workers = collectionOfWorkerManager.getAscending();
        for(Worker w:workers){
            messenger.output(w);
        }
    }
}
