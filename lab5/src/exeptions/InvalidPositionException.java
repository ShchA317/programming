package exeptions;

/**
 * сигнализирует о попытке определить несуществующую должность
 */
public class InvalidPositionException extends InvalidWorkerFieldException{
    public InvalidPositionException(){
        super("Invalid position has been entered.");
    }
}
