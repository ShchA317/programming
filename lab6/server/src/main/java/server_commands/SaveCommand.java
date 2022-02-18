package server_commands;

import command.Command;
import worker.CollectionOfWorkersManager;

import java.io.IOException;

public class SaveCommand implements Command {

    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager) throws IOException {
        collectionOfWorkersManager.save();
        log.Logback.getLogger().warn("collection was saved");
    }

    @Override
    public String getResult() {
        return null;
    }
}
