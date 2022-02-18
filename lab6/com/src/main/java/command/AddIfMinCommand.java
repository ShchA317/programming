package command;

import exceptions.NotTheSmallestException;
import worker.CollectionOfWorkersManager;
import java.io.Serializable;

public class AddIfMinCommand extends CommandWithWorkerArg implements Serializable {
    String result;

    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager) {
        if(collectionOfWorkersManager.collectionIsEmpty()){
            collectionOfWorkersManager.addWorker(worker);
        } else {
            try {
                collectionOfWorkersManager.addIfMin(worker);
                result = "worker was added";
            } catch (NotTheSmallestException ntse) {
                result = "Not the smallest";
            }
        }
    }
    @Override
    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof AddIfMinCommand){
            return ((AddIfMinCommand) object).getWorker().equals(worker);
        } else {
            return false;
        }
    }
}
