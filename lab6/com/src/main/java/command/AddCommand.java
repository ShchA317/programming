package command;

import worker.CollectionOfWorkersManager;

import java.io.Serializable;

public class AddCommand extends CommandWithWorkerArg implements Serializable{
    private String result;

    public void execute(CollectionOfWorkersManager collectionOfWorkersManager) {
        collectionOfWorkersManager.addWorker(worker);
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
