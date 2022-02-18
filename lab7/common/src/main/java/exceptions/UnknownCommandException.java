package exceptions;

public class UnknownCommandException extends RuntimeException{
    public UnknownCommandException(String msg){super(msg);}
    public UnknownCommandException(){
        super("Неизвестная команда");
    }
}
