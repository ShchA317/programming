package command;

import worker.CollectionOfWorkersManager;
import worker.Worker;

import java.io.Serializable;
import java.util.List;

public class PrintAscendingCommand extends SimpleCommand implements Serializable {
    String result;
    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager) {
        if(collectionOfWorkersManager.collectionIsEmpty()) {
            result = "collection is empty";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ascending list of workers:\n");
            List<Worker> workers = collectionOfWorkersManager.getAscending();
            for (Worker w : workers) {
                stringBuilder.append(w.toFormalString());
            }
            result = new String(stringBuilder);
        }
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof PrintAscendingCommand){
            return true;
        }
        return false;
    }
}
