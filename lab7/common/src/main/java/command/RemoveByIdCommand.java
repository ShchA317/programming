package command;

import exceptions.NoSuchWorkerException;
import user.Auth;
import worker.CollectionOfWorkersManager;

import java.io.Serializable;

public class RemoveByIdCommand implements Serializable, SimpleCommandWithArg, Cloneable {
    private long id;
    String result;
    @Override
    public void execute() {

    }

    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager, Auth auth) {
        if(collectionOfWorkersManager.collectionIsEmpty()) {
            result = "collection is empty";
        } else {
            try {
                collectionOfWorkersManager.removeById(id, auth);
                result = "the item was deleted";
            } catch (NoSuchWorkerException nswe){
                result = "Worker was not found";
            }
        }
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public void setSimpleArg(String s) {
        id = Long.parseLong(s);
    }

    @Override
    public SimpleCommandWithArg clone() {
        try {
            return (RemoveByIdCommand)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getId(){
        return id;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof  RemoveByIdCommand){
            return ((RemoveByIdCommand) o).getId() == id;
        }
        return false;
    }
}