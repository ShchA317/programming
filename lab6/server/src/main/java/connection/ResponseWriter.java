package connection;

import network.Response;

public interface ResponseWriter {
    Response writeResponse(String s);
}
