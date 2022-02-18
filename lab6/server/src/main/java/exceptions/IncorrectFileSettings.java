package exceptions;

/**
 * класс-исключение, кидается когда есть проблемы с настройками доступа к файлу
 * говорит о том, что не удалось получить доступ к фалу из-за проблем с его настройкой
 */
public class IncorrectFileSettings extends RuntimeException{
    public IncorrectFileSettings(){
        super("Incorrect file settings.");
    }
}
