package network;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

public class Response implements Serializable {
    final String message;
    final boolean success;
    private static final long serialVersionUID = -5622461857486946378L;
    private transient SocketChannel socketChannel;

    public Response(String message, boolean success){
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
}
