package main;

import main.worker.Worker;

/**
 * интерфейс для классов вывода
 */
public interface Messenger {
    void output(String string);
    void output(Worker worker);
}
