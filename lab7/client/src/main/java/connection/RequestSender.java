package connection;

import network.Request;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public interface RequestSender {
    void sendRequest(Request request, SocketChannel socketChannel) throws IOException;
    void initOutputStream(SocketChannel socketChannel) throws IOException;
}
