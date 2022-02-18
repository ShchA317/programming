package connection;

import network.Request;
import network.Response;

public interface RequestHandler {
    Response handleRequest(Request request);
}
