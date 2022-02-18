package server;
import application.Application;

public class Main {
    public static void main(String[] ars){
        Application application = new ServerApplication();
        application.start();
    }
}
