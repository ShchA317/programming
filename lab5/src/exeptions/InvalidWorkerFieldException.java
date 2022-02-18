package exeptions;

/**
 * сигнализирует о попытке выставить поля работяге, которые не отвечают формату
 */
public class InvalidWorkerFieldException extends IllegalArgumentException {

    public InvalidWorkerFieldException() {
        super("Illegal worker field has been entered.");
    }

    public InvalidWorkerFieldException(String message) {
        super(message);
    }
}