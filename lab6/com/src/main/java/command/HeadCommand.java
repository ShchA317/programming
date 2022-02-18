package command;

import worker.CollectionOfWorkersManager;
import worker.Worker;

import java.io.Serializable;

public class HeadCommand extends SimpleCommand implements Serializable {
    String result;
    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager) {
        if(collectionOfWorkersManager.collectionIsEmpty()){
            result = "collection is empty";
        } else {
            Worker worker = collectionOfWorkersManager.getHead();
            result = worker.toFormalString();
        }
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof HeadCommand){
            return true;
        }
        return false;
    }
}
