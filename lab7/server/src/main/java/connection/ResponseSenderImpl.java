package connection;

import network.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ResponseSenderImpl implements ResponseSender {
    Selector selector;

    public void sendResponse(SocketChannel socketChannel, Response response) throws IOException {
        sendBytes(serializeResponse(response), socketChannel);
    }

    private void sendBytes(byte[] bytes, SocketChannel socketChannel) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        int numWritten = 1;
        try {
            while (byteBuffer.remaining() > 0) {
                numWritten = socketChannel.write(byteBuffer);
            }
        } catch (IOException e) {
            log.Logback.getLogger().error("IOException");
            log.Logback.getLogger().error("error with sending response");
            socketChannel.close();
        }
    }

    private byte[] serializeResponse(Response response) throws IOException{
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(byteStream);
        stream.writeObject(response);
        stream.flush();
        return byteStream.toByteArray();
    }
}
