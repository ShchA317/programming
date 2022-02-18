package command;

import user.Auth;
import worker.CollectionOfWorkersManager;

import java.io.IOException;

public interface Command{
    default void execute() throws IOException {}
    void execute(CollectionOfWorkersManager collectionOfWorkersManager, Auth auth) throws IOException;
    String getResult();
}
