package command;

import org.junit.jupiter.api.Test;
import worker.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AddCommandTest {

    @Test
    void testEquals() {
        CommandWithWorkerArg command = new AddCommand();
        command.setArg(new OrdinaryWorker());
        CommandWithWorkerArg addCommandWithOrdinaryWorker = new AddCommand();
        addCommandWithOrdinaryWorker.setArg(new OrdinaryWorker());

        assertEquals(command, addCommandWithOrdinaryWorker);

        OrdinaryWorker worker = new OrdinaryWorker();
        worker.setName("test");
        worker.setSalary(100.0);
        command.setArg(worker);

        assertNotEquals(command, addCommandWithOrdinaryWorker);

        addCommandWithOrdinaryWorker.setArg(new OrdinaryWorker(worker));

        assertEquals(command, addCommandWithOrdinaryWorker);


        AddCommand add = new AddCommand();
        add.setArg(new OrdinaryWorker(
                "Name",
                new OrdinaryCoordinates(1.0,2),
                1000.0,
                null,
                Position.LEAD_DEVELOPER,
                Status.RECOMMENDED_FOR_PROMOTION,
                null
        ));

        AddCommand add2 = new AddCommand();
        OrdinaryWorker worker1 = new OrdinaryWorker();

        worker1.setName("Name");
        worker1.setSalary(1000.0);
        worker1.setCoordinates(new OrdinaryCoordinates(1.0, 2));
        worker1.setStatus(Status.RECOMMENDED_FOR_PROMOTION);
        worker1.setPosition(Position.LEAD_DEVELOPER);

        add2.setArg(worker1);

        assertEquals(add, add2);
    }
}