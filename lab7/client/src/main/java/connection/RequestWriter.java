package connection;

import command.Command;
import network.Request;
import user.Auth;

public interface RequestWriter {
    Request writeRequest(Command command, Auth auth);
}
