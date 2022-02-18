package connection;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ConnectionManagerImpl implements ConnectionManager{
    private SocketChannel socketChannel;

    @Override
    public SocketChannel openConnection(String address, int port) throws IOException {
        socketChannel = SocketChannel.open(new InetSocketAddress(address, port));
        return socketChannel;
    }

    @Override
    public void closeConnection() throws IOException {
        socketChannel.close();
    }
}
