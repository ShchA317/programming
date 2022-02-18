package network;

import java.io.Serializable;

public class Response implements Serializable {
    String message;
    boolean success;
    private static final long serialVersionUID = -5622461857486946378L;

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
}
