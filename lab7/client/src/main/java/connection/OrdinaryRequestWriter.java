package connection;

import command.Command;
import network.Request;
import user.Auth;

public class OrdinaryRequestWriter implements RequestWriter{
    @Override
    public Request writeRequest(Command command, Auth auth) {
        return new Request(command, auth);
    }
}
