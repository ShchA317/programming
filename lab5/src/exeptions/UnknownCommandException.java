package exeptions;

/**
 * сигнализирует о попытке исполнить несуществующую команду
 */
public class UnknownCommandException extends RuntimeException{
    public UnknownCommandException() {
        super("Error. Unknown command has been entered.");
    }
}
