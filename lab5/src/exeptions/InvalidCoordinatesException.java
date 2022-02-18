package exeptions;

/**
 * сигнализирует о попытке выставить координаты, не отвечающие формату
 */
public class InvalidCoordinatesException extends InvalidWorkerFieldException{
    public InvalidCoordinatesException() {
        super("No coordinates have been entered.");
    }
}
