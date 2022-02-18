package command;

public interface SimpleCommandWithArg extends Command {
    void setSimpleArg(String s);
    SimpleCommandWithArg clone();
}
