package exeptions;

/**
 * сигнализирует о попытке выставить имя, не отвечающее формату
 */
public class InvalidNameException extends InvalidWorkerFieldException{
    public InvalidNameException(){
        super("Invalid name has been entered.");
    }
}
