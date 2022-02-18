package connection;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;

public class ServerConnectionManagerImpl2 {
    private Selector selector;
    private InetSocketAddress socketAddress;


    public Selector openConnection() throws IOException {
        selector = SelectorProvider.provider().openSelector();

        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        try {
            serverChannel.socket().bind(socketAddress);
        } catch (BindException be){
            log.Logback.getLogger().error("be");
            be.printStackTrace();
        }
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        return selector;
    }

    public void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (NotYetBoundException notYetBoundException){
            log.Logback.getLogger().error(notYetBoundException.getMessage());
            log.Logback.getLogger().error("no yet bound exception");
            notYetBoundException.printStackTrace();
            System.exit(-1001);
        }

    }

    public Selector getSelector() {
        return selector;
    }

    public void setSocketAddress(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }
}
