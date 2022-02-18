package application;

import java.io.IOException;

public interface Application {
    void start();
    void exit() throws IOException;
}
