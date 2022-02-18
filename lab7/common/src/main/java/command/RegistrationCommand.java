package command;

import user.Auth;
import worker.CollectionOfWorkersManager;

import java.io.IOException;
import java.io.Serializable;

public class RegistrationCommand extends UsersCommand implements Serializable {

    public RegistrationCommand(){

    }

    public String getLogin() {
        return login;
    }

    public String getPassword(){
        return password;
    }

    @Override
    public SimpleCommandWithArg clone() {
        SimpleCommandWithArg simpleCommandWithArg = super.clone();
        return null;
    }

    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager, Auth auth) {

    }
}
