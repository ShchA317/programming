package connection;

import network.Response;

import java.io.*;
import java.nio.channels.SocketChannel;

public class ResponseReaderIml implements ResponseReader{
    @Override
    public Response getResponse(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        byte[] bytes = new byte[16384];
        InputStream stream = socketChannel.socket().getInputStream();
        stream.read(bytes);
        return deserializeResponse(bytes);
    }

    private Response deserializeResponse(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        ObjectInputStream stream = new ObjectInputStream(byteStream);
        return (Response) stream.readObject();
    }

}

