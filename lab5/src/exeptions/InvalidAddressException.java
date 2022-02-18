package exeptions;

/**
 * сигнализирует о попытке выставить адрес, не отвечающий формату
 */
public class InvalidAddressException extends InvalidWorkerFieldException{
    public InvalidAddressException(){
        super("Invalid address has been entered.");
    }
}
