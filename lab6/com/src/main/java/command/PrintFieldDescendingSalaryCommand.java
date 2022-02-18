package command;

import worker.CollectionOfWorkersManager;

import java.io.Serializable;

public class PrintFieldDescendingSalaryCommand extends SimpleCommand implements Serializable {
    String result;
    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager) {
        if(collectionOfWorkersManager.collectionIsEmpty()) {
            result = "collection is empty";
        } else {
            Double[] doubles = collectionOfWorkersManager.getFieldDescendingSalary();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Salaries of employees in descending order:\n");
            for (Double d : doubles) {
                stringBuilder.append(d.toString() + "\n");
            }
            result = stringBuilder.toString();
        }
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o){
        return o instanceof PrintFieldDescendingSalaryCommand;
    }
}
