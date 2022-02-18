package command_execution;

import command.Command;

public interface CommandExecutor {
    String executeCommand(Command command);
}
