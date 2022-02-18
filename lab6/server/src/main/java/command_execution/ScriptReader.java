package command_execution;

import command.Command;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface ScriptReader {
    List<Command> getScript(String scriptFileName) throws IOException, ParseException;
    Command getNextCommand() throws IOException;
}
