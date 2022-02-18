package connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerConnectionManagerImpl implements ServerConnectionManager{
    private Selector selector;
    private ServerSocketChannel serverChannel;
    @Override
    public void openConnection(String address, int port) throws IOException {
        selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(address, port));
        serverChannel.configureBlocking(false);
        int ops = serverChannel.validOps();
        serverChannel.register(selector, ops);
    }

    @Override
    public Selector listen() throws IOException {
        selector.select();
        Set<SelectionKey> keys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = keys.iterator();

        while(iterator.hasNext()) {
            SelectionKey key = iterator.next();
            if (key.isAcceptable()) {
                SocketChannel connectedChannel = serverChannel.accept();
                connectedChannel.configureBlocking(false);
                connectedChannel.register(selector, SelectionKey.OP_READ);
            }
            iterator.remove();
        }

        return selector;
    }

    @Override
    public void stop() throws IOException {
        selector.close();
        serverChannel.socket().close();
        serverChannel.close();
    }

    public Selector getSelector() {
        return selector;
    }
}
