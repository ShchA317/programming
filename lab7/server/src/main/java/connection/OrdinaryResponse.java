package connection;

import java.io.Serializable;

public class OrdinaryResponse implements Serializable {
    final String message;
    final boolean success;

    public OrdinaryResponse(String message, boolean success){
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
