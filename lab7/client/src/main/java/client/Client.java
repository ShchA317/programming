package client;
import application.Application;

public class Client {
    public static void main(String[] args){
        String address = "localhost";
        int port = 8888;
        Application application = new ClientApplication(address, port);
        try {
            application.start();
        } catch (Exception e){
            System.err.println("ERROR \n Let's try again");
        }
    }
}
