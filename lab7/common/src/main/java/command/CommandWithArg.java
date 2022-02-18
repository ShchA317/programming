package command;

public interface CommandWithArg extends Command{
    @Override
    default String getResult() {
        return null;
    }
}
