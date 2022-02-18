package exceptions;

/**
 * сигнализирует о попытке выставить адрес, не отвечающий формату
 */
public class ScriptFileRecursionException extends  RuntimeException{
    public ScriptFileRecursionException() {
        super("Error. Script file contains recursion.");
    }
}
