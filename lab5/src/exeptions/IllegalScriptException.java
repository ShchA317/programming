package exeptions;

/**
 * класс-исключение, кидается во время выполнения скрипта
 * говорит о том, что во время выполнения скрипта произошла ошибка
 */
public class IllegalScriptException extends RuntimeException {
    public IllegalScriptException() {
        super("Errors with script");
    }
}