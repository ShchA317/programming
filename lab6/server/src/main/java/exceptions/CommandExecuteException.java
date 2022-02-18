package exceptions;

public class CommandExecuteException extends RuntimeException {
    CommandExecuteException(){
        super("Ошибка во время выполнения команды");
    }
}
