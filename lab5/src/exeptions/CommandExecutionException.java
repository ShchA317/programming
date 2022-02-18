package exeptions;

/**
 * класс-исключение, кидается во время выполнения команды
 * говорит о том, что во время выполнения команды произошла ошибка
 */
public class CommandExecutionException extends RuntimeException{
    public CommandExecutionException(){
        super("An error occurred during execution");
    }
}
