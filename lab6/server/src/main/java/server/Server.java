package server;
import application.Application;

public class Server {
    public static void main(String[] ars){
        Application application = new ServerApplication();
        application.start();
    }
}
