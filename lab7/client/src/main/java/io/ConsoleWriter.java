package io;

public class ConsoleWriter implements ClientWriter {
    @Override
    public void write(String string) {
        System.out.println(string);
    }
}
