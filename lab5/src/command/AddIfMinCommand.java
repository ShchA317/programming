package command;

import exeptions.CommandExecutionException;
import main.CollectionOfWorkerManager;
import main.CommandReader;
import main.Messages;
import main.Messenger;
import main.worker.Worker;

/**
 * this command adds worker in collection if this worker is min
 */
public class AddIfMinCommand implements Command{
    public AddIfMinCommand(){

    }
    @Override
    public void execute(Messages messages, CommandReader reader, Messenger messenger,
                        String arg, CollectionOfWorkerManager collectionOfWorkerManager){
        Worker worker;
        try{
            collectionOfWorkerManager.addIfMin(reader.readWorker());
        } catch (Exception e){
            throw new CommandExecutionException();
        }

    }
}
