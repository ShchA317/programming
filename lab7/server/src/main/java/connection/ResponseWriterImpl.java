package connection;

import network.Response;

public class ResponseWriterImpl implements ResponseWriter {
    @Override
    public Response writeResponse(String s) {
        return new Response(s, true);
    }

    @Override
    public Response writeResponse(String s, boolean success) {
        return new Response(s, success);
    }
}
