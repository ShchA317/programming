package connection;

import command.Command;
import network.Request;

public interface RequestWriter {
    Request writeRequest(Command command);
}
