package command;

import user.Auth;
import worker.CollectionOfWorkersManager;

import java.io.Serializable;

public class AddCommand extends CommandWithWorkerArg implements Serializable{
    private String result;

    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager, Auth auth) {
        collectionOfWorkersManager.addWorker(worker, auth);
        result = "worker was added";
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof AddCommand){
            return ((AddCommand) object).getWorker().equals(worker);
        } else {
            return false;
        }
    }
}
