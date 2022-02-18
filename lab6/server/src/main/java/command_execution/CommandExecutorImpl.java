package command_execution;

import worker.CollectionOfWorkersManager;
import command.Command;
import server_commands.ExecuteScriptCommand;
import exceptions.CommandExecuteException;

import java.io.IOException;

public class CommandExecutorImpl implements CommandExecutor {
    CollectionOfWorkersManager collectionOfWorkersManager;

    public CommandExecutorImpl(CollectionOfWorkersManager collectionOfWorkersManager) {
        this.collectionOfWorkersManager = collectionOfWorkersManager;
    }

    @Override
    public String executeCommand(Command command) {
        try {
            if (command instanceof ExecuteScriptCommand) {
                command.execute();
            } else {
                command.execute(collectionOfWorkersManager);
            }
            log.Logback.getLogger().info(command + " was executed");

            return command.getResult();

        } catch (CommandExecuteException | IOException cee){
            return "не получилось, не фартануло";
        }
    }
}
