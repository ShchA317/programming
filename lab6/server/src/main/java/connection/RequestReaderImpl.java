package connection;

import network.Request;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class    RequestReaderImpl implements RequestReader{
    private Selector selector;

    @Override
    public Request readRequest(Selector selector) throws IOException, ClassNotFoundException {
        this.selector = selector;
        return deserializeRequest(readBytes());
    }

    private Request deserializeRequest(byte[] bytes) throws IOException, ClassNotFoundException{
        ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(bytes));
        return (Request) stream.readObject();
    }

    private byte[] readBytes() throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(2048);
        SocketChannel channel = null;
        while (channel == null) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();

            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isReadable()) {
                    channel = (SocketChannel)key.channel();
                    channel.read(buf);
                    channel.register(selector, SelectionKey.OP_WRITE);
                }
                iterator.remove();
            }
        }
        return buf.array();
    }
}
