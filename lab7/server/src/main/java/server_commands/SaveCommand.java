package server_commands;

import command.Command;
import data.DataManager;
import user.Auth;
import worker.CollectionOfWorkersManager;

import java.io.IOException;

public class SaveCommand implements Command {

    public SaveCommand(DataManager dataManager){

    }

    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager, Auth auth) {

    }

    @Override
    public String getResult() {
        return null;
    }
}
