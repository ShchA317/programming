package main;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import exeptions.NoSuchWorkerException;
import main.worker.*;

/**
 * concrete implementation CollectionOfWorkersManager for LinkedList-collection
 */
public class ListOfWorkerManager implements CollectionOfWorkerManager{
    private ZonedDateTime creationDate;
    private LinkedList<Worker> listOfWorkers = new LinkedList<Worker>();
    private LinkedList<Worker> bufferListOfWorkers = new LinkedList<Worker>();
    private WorkerWriter workerWriter;
    private WorkerReader workerReader;

    public ListOfWorkerManager(WorkerReader jsonFileWorkerReader, WorkerWriter jsonFileWorkerWriter){
        creationDate = ZonedDateTime.now();
        this.workerReader = jsonFileWorkerReader;
        this.workerWriter = jsonFileWorkerWriter;
    }

    @Override
    public Worker getWorker(int number) {
        return listOfWorkers.get(number);
    }

    @Override
    public void addWorker(Worker worker){
        listOfWorkers.add(worker);
    }

    @Override
    public String getInfo() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
        String result = "Collection: linked list of workers " +
                "\nCreation date: " + creationDate.format(format) +
                "\nNumber of elements: " + listOfWorkers.size();
        return result;
    }

    @Override
    public void updateWorkerById(long id, Worker worker) {
        int index = listOfWorkers.indexOf(getWorkerByID(id));
        listOfWorkers.remove(index);
        listOfWorkers.add(index, worker);
    }

    @Override
    public void removeById(long id) {
        listOfWorkers.remove(getWorkerByID(id));
    }

    @Override
    public void clear() {
        listOfWorkers.clear();
    }

    @Override
    public void save() {
        workerWriter.saveWorkers(listOfWorkers);
    }

    @Override
    public Worker getHead() {
        return listOfWorkers.getFirst();
    }

    @Override
    public Worker removeHead() {
        OrdinaryWorker worker = (OrdinaryWorker) listOfWorkers.getFirst();
        OrdinaryWorker.removeIdFromSet(worker.getId());
        return listOfWorkers.remove();

    }

    @Override
    public boolean addIfMin(Worker worker) {
        Worker min = Collections.min(listOfWorkers);
        if (worker.compareTo(min) < 0){
            listOfWorkers.add(worker);
            return true;
        }
        return false;
    }

    @Override
    public long countLessThanOrganization(Organization organization) {
        LinkedList<Worker> list = this.listOfWorkers;
        for (Worker w : listOfWorkers){
            if(w.getOrganization().compareTo(organization) < 0){
                list.add(w);
            }
        }
        return 0;
    }

    @Override
    public LinkedList getAscending() {
        LinkedList<Worker> list = this.listOfWorkers;
        Comparator<Worker> comparator = new Comparator<Worker>(){
            @Override
            public int compare(Worker worker, Worker t1) {
                return worker.compareTo(t1);
            }
        };
        list.sort(comparator);
        return list;
    }

    @Override
    public Double[] getFieldDescendingSalary() {
        Double[] result = new Double[listOfWorkers.size()];
        int i = 0;
        for(Worker w:listOfWorkers){
            result[i] = w.getSalary();
            i++;
        }
        return result;
    }

    @Override
    public void parseDataToCollection() {
       listOfWorkers = (LinkedList<Worker>) workerReader.getWorkers();
    }

    @Override
    public int getSize() {
        return listOfWorkers.size();
    }

    @Override
    public void saveBeforeDangerActions() {
        bufferListOfWorkers = this.listOfWorkers;
    }

    @Override
    public void recover() {
        this.listOfWorkers = bufferListOfWorkers;
    }


    public Worker getWorkerByID(long id){
        if(!listOfWorkers.isEmpty()) {
            for (Worker i : listOfWorkers) {
                if (i.getId() == id) {
                    return i;
                }
            }
        }
        throw new NoSuchWorkerException();
    }

}
