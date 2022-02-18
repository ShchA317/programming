package command;

import exeptions.ScriptFileRecursionException;
import main.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

/**
 * класс-команда для исполнения пользовательского скрипта
 */
public class ExecuteScriptCommand implements Command{
    public ExecuteScriptCommand(){

    }

    /**
     * стек скриптов, находящихся в исполнении
     */
    private static Stack<String> scripts = new Stack<>();
    @Override
    public void execute(Messages messages, CommandReader reader, Messenger messenger,
                        String arg, CollectionOfWorkerManager collectionOfWorkerManager) throws IOException {
        HashMap<String, Command> commands = new HashMap<>();
        commands.put("add", new AddCommand());
        commands.put("add_if_min", new AddIfMinCommand());
        commands.put("clear", new ClearCommand());
        commands.put("count_less_than_organization", new CountLessThanOrganizationCommand());
        commands.put("execute_script", new ExecuteScriptCommand());
        commands.put("exit", new ExitCommand());
        commands.put("head", new HeadCommand());
        commands.put("help", new HelpCommand());
        commands.put("info", new InfoCommand());
        commands.put("print_ascending", new PrintAscendingCommand());
        commands.put("print_field_descending_salary", new PrintFieldDescendingSalaryCommand());
        commands.put("remove_by_id", new RemoveByIdCommand());
        commands.put("remove_head", new RemoveHeadCommand());
        commands.put("save", new SaveCommand());
        commands.put("show", new ShowCommand());
        commands.put("update", new UpdateCommand());

        CommandFactory commandFactory = new ConcreteCommandFactory(commands, messages);

        if(scripts != null && arg != null){
            if(scripts.contains(arg)) {
                throw new ScriptFileRecursionException();
            }
        }
        scripts.push(arg);
        ScriptReader scriptReader = new ScriptReader(arg, collectionOfWorkerManager, messages, messenger, commandFactory);
        scriptReader.readCommands();
        messenger.output("script " + scripts.pop() + " was executed");
    }
}
