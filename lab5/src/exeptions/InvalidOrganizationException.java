package exeptions;

/**
 * сигнализирует о попытке определить организацию, не отвечающую формату
 */
public class InvalidOrganizationException extends InvalidWorkerFieldException{
    public InvalidOrganizationException(){
        super("Invalid organization has been entered.");
    }
}
