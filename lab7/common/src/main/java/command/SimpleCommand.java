package command;

import java.io.Serializable;

public abstract class SimpleCommand implements Command, Serializable {
    protected boolean success;

    @Override
    public void execute() {

    }

    @Override
    public String getResult() {
        return null;
    }
}

