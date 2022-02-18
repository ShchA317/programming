package server_commands;

import application.Application;
import command.Command;
import worker.CollectionOfWorkersManager;

import java.io.IOException;

public class ExitCommand implements Command {
    Application application;
    public ExitCommand(Application application) throws IOException {
        this.application = application;
    }

    public void execute() throws IOException {
        application.exit();
    }

    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager) throws IOException {
        application.exit();
    }

    @Override
    public String getResult() {
        return null;
    }
}
