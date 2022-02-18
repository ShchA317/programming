package server_commands;

import application.Application;
import command.Command;
import user.Auth;
import worker.CollectionOfWorkersManager;

import java.io.IOException;

public class ExitCommand implements Command {
    final Application application;
    public ExitCommand(Application application) {
        this.application = application;
    }

    public void execute() throws IOException {
        application.exit();
    }

    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager, Auth auth) throws IOException {
        application.exit();
    }

    @Override
    public String getResult() {
        return null;
    }
}
