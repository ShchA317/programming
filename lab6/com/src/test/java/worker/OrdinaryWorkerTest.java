package worker;

import exceptions.InvalidWorkerFieldException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OrdinaryWorkerTest {
    Worker worker = new OrdinaryWorker();
    @Test
    void TestSetName() {
        String normName = "Norm Name";
        worker.setName(normName);
        Assertions.assertEquals(normName, worker.getName());
        try{
            worker.setName(null);
        } catch (InvalidWorkerFieldException iwfe){
            System.out.println(iwfe.getMessage());
        }
    }

    @Test
    void TestSetSalary() {
        Double goodSalary = 99999.0;
        worker.setSalary(goodSalary);
        Assertions.assertEquals(goodSalary, worker.getSalary());
        try{
            worker.setSalary(-10.0);
            throw new RuntimeException();
        } catch (InvalidWorkerFieldException ignored) {

        }
        try {
            worker.setSalary(null);
        } catch (InvalidWorkerFieldException ignored){

        }
        Assertions.assertEquals(goodSalary, worker.getSalary());
        Double veryGoodSalary = 9999999999999999999999999999999999999999999999999.99999999999999999999999999999999999999999999;
        worker.setSalary(veryGoodSalary);
        Assertions.assertEquals(veryGoodSalary, worker.getSalary());
    }
}