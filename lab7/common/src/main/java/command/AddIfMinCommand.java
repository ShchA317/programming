package command;

import exceptions.NotTheSmallestException;
import user.Auth;
import worker.CollectionOfWorkersManager;
import java.io.Serializable;

public class AddIfMinCommand extends CommandWithWorkerArg implements Serializable {
    private String result;


    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager, Auth auth) {
        if(collectionOfWorkersManager.collectionIsEmpty()){
            collectionOfWorkersManager.addWorker(worker, auth);
        } else {
            try {
                collectionOfWorkersManager.addIfMin(worker, auth);
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
