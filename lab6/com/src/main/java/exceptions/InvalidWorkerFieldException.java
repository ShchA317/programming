package exceptions;

import java.util.Locale;
import java.util.ResourceBundle;

public class InvalidWorkerFieldException extends RuntimeException{
   // ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag("ru-RU"));
    public InvalidWorkerFieldException(String message){
        super(message);
    }
    public InvalidWorkerFieldException(){

    }
}
