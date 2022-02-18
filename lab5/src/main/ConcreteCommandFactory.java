package main;

import command.Command;
import exeptions.CommandExecutionException;
import exeptions.InvalidWorkerFieldException;
import exeptions.UnknownCommandException;

import java.io.IOException;
import java.util.Map;

/**
 * реализация фабрики команд
 */
public class ConcreteCommandFactory implements CommandFactory {

    private final Map<String,Command> commands;
    private final Messages messages;

    public ConcreteCommandFactory(Map<String, Command> commands, Messages messages) {
        this.commands = commands;
        this.messages = messages;
    }

    @Override
    public void executeCommand(String commandName, CommandReader commandReader,
                               String args, CollectionOfWorkerManager collectionOfWorkerManager) {
        Command command;
        Messenger messenger = new ConsoleMessenger();
            command = getCommand(commandName);
            if(command == null){
                throw new UnknownCommandException();
            }
        try {
            command.execute(messages, commandReader, messenger, args, collectionOfWorkerManager);
        }catch (IOException e){
            System.err.println("IO exception");
        } catch (UnknownCommandException e){
            System.err.println("Unknown command");
        }
    }

    private Command getCommand(String commandName){
        return commands.get(commandName);
    }

}
