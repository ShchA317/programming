package connection;

import network.Response;

public interface ResponseWriter {
    Response writeResponse(String s);
    Response writeResponse(String s, boolean success);
}
