package command;

import main.CollectionOfWorkerManager;
import main.CommandReader;
import main.Messages;
import main.Messenger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * class-command for output salaries in descending order
 */
public class PrintFieldDescendingSalaryCommand implements Command{

    @Override
    public void execute(Messages messages, CommandReader reader, Messenger messenger,
                        String arg, CollectionOfWorkerManager collectionOfWorkerManager) throws IOException {
        Double[] array = collectionOfWorkerManager.getFieldDescendingSalary();
        Arrays.sort(array, Collections.reverseOrder());
        for(Double d:array){
            messenger.output(d.toString());
        }
    }
}
