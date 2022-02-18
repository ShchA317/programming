package connection;

import network.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.channels.SocketChannel;

public class OrdinaryRequestSender implements RequestSender {
    private OutputStream stream;
    public void initOutputStream(SocketChannel socketChannel) throws IOException {
        stream = socketChannel.socket().getOutputStream();
    }

    @Override
    public void sendRequest(Request request, SocketChannel socketChannel) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
        objectOutputStream.writeObject(request);
        this.stream.write(byteStream.toByteArray());
        this.stream.flush();
    }
}
