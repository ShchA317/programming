package command;

import exceptions.NoSuchWorkerException;
import worker.CollectionOfWorkersManager;
import worker.Worker;

import java.io.Serializable;

public class UpdateCommand extends CommandWithWorkerArg implements SimpleCommandWithArg, Serializable, Cloneable {
    long id;
    String result;
    @Override
    public void setSimpleArg(String s) {
        id = Long.parseLong(s);
    }

    @Override
    public UpdateCommand clone() {
        return (UpdateCommand)super.clone();
    }

    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager) {
        if(collectionOfWorkersManager.collectionIsEmpty()) {
            result = "collection is empty";
        } else {
            try {
                collectionOfWorkersManager.updateWorkerById(id, worker);
                result = "Worker has been updated";
            } catch (NoSuchWorkerException nswe){
                result = "Worker was not found";
            }
        }
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof UpdateCommand){
            return ((UpdateCommand) o).id == id && ((UpdateCommand) o).getWorker().equals(worker);
        }
        return false;
    }
}
