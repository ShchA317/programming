package exeptions;
import java.util.NoSuchElementException;

/**
 * сигнализирует о попытке найти несуществующего работягу
 */
public class NoSuchWorkerException extends NoSuchElementException {
    public NoSuchWorkerException(){
        super("There is no such worker on the list or list is empty");
    }
}
