package command;

import user.Auth;
import worker.CollectionOfWorkersManager;

import java.io.Serializable;

public class InfoCommand extends SimpleCommand implements Serializable {
    String result;
    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager, Auth auth) {
        result = collectionOfWorkersManager.getInfo();
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o){
        return o instanceof InfoCommand;
    }
}
