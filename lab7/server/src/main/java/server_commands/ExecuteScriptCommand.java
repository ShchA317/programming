package server_commands;

import command.Command;
import command_execution.ScriptReader;
import user.Auth;
import worker.CollectionOfWorkersManager;

import java.io.Serializable;
import java.util.List;

public class ExecuteScriptCommand implements Serializable, Cloneable, Command {
    private final ScriptReader scriptReader;

    public ExecuteScriptCommand(ScriptReader scriptReader){
        this.scriptReader = scriptReader;
    }

    @Override
    public ExecuteScriptCommand clone() {
        try {
            return (ExecuteScriptCommand) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void execute(CollectionOfWorkersManager collectionOfWorkersManager, String scriptFileName) {

        collectionOfWorkersManager.saveBeforeDangerActions();
        String result;
        try {
            List<Command> commandList = scriptReader.getScript(scriptFileName);
            for (Command command : commandList) {
                command.execute(collectionOfWorkersManager, new Auth("script", "jepriufhewpua4grw"));
            }
            result = "script was executed";
            log.Logback.getLogger().info(result);
        } catch (Exception e) {

            result = "collection was recovered";
            log.Logback.getLogger().error(result);
            if(e.getMessage() != null) {
                log.Logback.getLogger().error(e.getMessage());
                log.Logback.getLogger().error("execute script error");
            } else{
                log.Logback.getLogger().error(e.toString());
            }
            e.printStackTrace();
        }
    }


    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager, Auth auth) {

    }

    @Override
    public String getResult() {
        return null;
    }
}
