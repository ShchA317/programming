package connection;

import network.Response;

import java.io.IOException;
import java.nio.channels.Selector;

public interface ResponseSender {
    void sendResponse(Response response, Selector selector) throws IOException, ClassNotFoundException;
}
