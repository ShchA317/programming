package connection;

import network.Request;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class RequestReaderImpl implements RequestReader{
    private Selector selector;
    private SelectionKey selectionKey;

    @Override
    public Request readRequest(SelectionKey selectionKey) throws IOException, ClassNotFoundException {
        this.selectionKey = selectionKey;
        return deserializeRequest(readBytes());
    }

    private Request deserializeRequest(byte[] bytes) throws IOException, ClassNotFoundException{
        ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Request request = (Request) stream.readObject();
        request.setSocketChannel((SocketChannel) selectionKey.channel());
        return request;
    }

    private byte[] readBytes() throws IOException {
       ByteBuffer buf = ByteBuffer.allocate(2048);

        if(selectionKey.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            socketChannel.read(buf);
        } else {
            log.Logback.getLogger().error("NOT READABLE KEY");
        }

       return buf.array();
    }
}
