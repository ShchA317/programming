package exeptions;

/**
 * сигнализирует о попытке выставить статус, не отвечающий формату
 */
public class InvalidStatusException extends InvalidWorkerFieldException{
    public InvalidStatusException(){
        super("Invalid status has been entered.");
    }
}
