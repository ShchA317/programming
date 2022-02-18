package command;

import user.Auth;
import worker.CollectionOfWorkersManager;

import java.io.IOException;
import java.io.Serializable;

public class AuthorizationCommand extends UsersCommand implements Serializable {

    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager, Auth auth) { }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public SimpleCommandWithArg clone() {
        SimpleCommandWithArg simpleCommandWithArg = super.clone();
        return null;
    }
}
