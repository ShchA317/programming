package command_execution;

import collection.JSONFileWorkerReader;
import collection.ListOfWorkerManager;
import command.AddCommand;
import command.Command;
import command.CommandWithArg;
import command.CommandWithWorkerArg;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import worker.CollectionOfWorkersManager;
import worker.OrdinaryCoordinates;
import worker.OrdinaryWorker;
import worker.Worker;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CommandExecutorImplTest {

    CollectionOfWorkersManager collectionOfWorkersManager = new ListOfWorkerManager(null, null) {
            @Override
            public void parseDataToCollection() {
                listOfWorkers.add(new OrdinaryWorker());
                listOfWorkers.add(new OrdinaryWorker());
                listOfWorkers.add(new OrdinaryWorker());
            }

        @Override
        public boolean collectionIsEmpty() {
            return false;
        }
    };

    @Test
    void executeCommand() {
        collectionOfWorkersManager.parseDataToCollection();
        CommandExecutor commandExecutor = new CommandExecutorImpl(collectionOfWorkersManager);
        CommandWithWorkerArg command = new AddCommand();
        Worker testWorker = new OrdinaryWorker();
        testWorker.setName("testName");
        command.setArg(new OrdinaryWorker());
        Assertions.assertEquals("worker was added", commandExecutor.executeCommand(command));
    }
}