package connection;

import command.Command;
import network.Request;

public class OrdinaryRequestWriter implements RequestWriter{
    @Override
    public Request writeRequest(Command command) {
        return new Request(command);
    }
}
