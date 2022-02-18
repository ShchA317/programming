package command_execution;

import command.Command;
import user.Auth;

public interface CommandExecutor {
    String executeCommand(Command command, Auth auth);
}
