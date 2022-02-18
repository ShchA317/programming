package main;

import command.*;
import exeptions.IncorrectFileSettings;
import java.util.Map;
import java.util.HashMap;

/**
 * главный класс программы
 */
public class Main {
    /**
     * имя файла с данными
     */
    public static String dataFileName;
    public static void main(String[] args) {
        try {
            dataFileName = System.getenv("DATA_FOR_LAB5");
//            dataFileName = "data.json";
            if(dataFileName == null){
                throw new IncorrectFileSettings();
            }
            Map<String, Command> commands = getCommands();
            Messages messages = new RUMessages(commands);
            CommandFactory commandFactory = new ConcreteCommandFactory(commands, messages);
            CollectionOfWorkerManager collectionOfWorkerManager =
                    new ListOfWorkerManager(new JSONFileWorkerReader(dataFileName),
                                            new JSONFileWorkerWriter(dataFileName));

            Messenger messenger = new ConsoleMessenger();
            CommandReader commandReader = new ConsoleCommandReader(messenger, commandFactory, collectionOfWorkerManager, messages);

            collectionOfWorkerManager.parseDataToCollection();
            commandReader.readCommands();
          }
         catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("ERROR :(\nLet's try again");
        }
    }

    /**
     * метод для получения карты с командами
     * @return карта с командами
     */
    public static Map<String, Command> getCommands() {
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
        return commands;
    }
}