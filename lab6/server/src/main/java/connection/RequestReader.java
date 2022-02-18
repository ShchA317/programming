package connection;

import network.Request;

import java.io.IOException;
import java.nio.channels.Selector;

public interface RequestReader {
    Request readRequest(Selector selector) throws IOException, ClassNotFoundException;
}
