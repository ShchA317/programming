package command;

import worker.CollectionOfWorkersManager;

import java.io.Serializable;

public class InfoCommand extends SimpleCommand implements Serializable {
    String result;
    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager) {
        result = collectionOfWorkersManager.getInfo();
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof InfoCommand){
            return true;
        }
        return false;
    }
}
