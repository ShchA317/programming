package command;

import worker.CollectionOfWorkersManager;

import java.io.Serializable;

public class CountLessThanOrganizationCommand extends CommandWithOrganizationArg implements Serializable {
    String result;
    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager) {
        if(organization == null){
            result = "как сравнивать с null? Я не знаю";
        } else {
            if (collectionOfWorkersManager.collectionIsEmpty()) {
                result = "collection is empty";
            } else {
                long res = collectionOfWorkersManager.countLessThanOrganization(organization);
                result = String.valueOf(res);
            }
        }
    }

    public String getResult(){
        return result;
    }
}
