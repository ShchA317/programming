package io;

import command.Command;

import java.io.IOException;

public interface CommandReader {
    Command readCommand() throws IOException;
}
