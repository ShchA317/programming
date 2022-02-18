package exeptions;

/**
 * сигнализирует о попытке выставить локацию, не отвечающую формату
 */
public class InvalidLocationException extends InvalidWorkerFieldException{
    public InvalidLocationException(){
        super("Invalid location has been entered.");
    }
}
