package exceptions;

public class InvalidWorkerFieldException extends RuntimeException{
   // ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag("ru-RU"));
    public InvalidWorkerFieldException(String message){
        super(message);
    }
    public InvalidWorkerFieldException(){

    }
}
