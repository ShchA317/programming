package command;

import user.Auth;
import worker.CollectionOfWorkersManager;
import worker.Worker;

import java.io.Serializable;

public class HeadCommand extends SimpleCommand implements Serializable {
    String result;
    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager, Auth auth) {
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
        return o instanceof HeadCommand;
    }
}
