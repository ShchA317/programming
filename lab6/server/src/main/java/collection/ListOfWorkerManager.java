package collection;

import exceptions.NoSuchWorkerException;
import exceptions.NotTheSmallestException;
import worker.CollectionOfWorkersManager;
import worker.Organization;
import worker.Worker;
import worker.OrdinaryWorker;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * concrete implementation worker.CollectionOfWorkersManager for LinkedList-collection
 */
public class ListOfWorkerManager implements CollectionOfWorkersManager {
    protected ZonedDateTime creationDate;
    protected LinkedList<Worker> listOfWorkers = new LinkedList<>();
    protected LinkedList<Worker> bufferListOfWorkers = new LinkedList<>();
    protected WorkerWriter workerWriter;
    protected WorkerReader workerReader;

    public ListOfWorkerManager(WorkerReader jsonFileWorkerReader, WorkerWriter jsonFileWorkerWriter){
        creationDate = ZonedDateTime.now();
        this.workerReader = jsonFileWorkerReader;
        this.workerWriter = jsonFileWorkerWriter;
    }

    @Override
    public Worker getWorkerByNumber(int number) {
        return listOfWorkers.get(number);
    }

    @Override
    public void addWorker(Worker worker) {
        worker.setId(OrdinaryWorker.getNewId());
        listOfWorkers.add(worker);
    }

    @Override
    public String getInfo() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
        return "Collection: linked list of workers " +
                "\nCreation date: " + creationDate.format(format) +
                "\nNumber of elements: " + (long) listOfWorkers.size();
    }

    @Override
    public void updateWorkerById(long id, Worker newWorker) {
        int index = listOfWorkers.indexOf(getWorkerByID(id));
        listOfWorkers.remove(index);
        listOfWorkers.add(index, newWorker);
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
    public void addIfMin(Worker worker) {
        Worker min = listOfWorkers.stream().min(Worker::compareTo).orElse(null);
        assert min != null;
        if (worker.compareTo(min) < 0){
            worker.setId(OrdinaryWorker.getNewId());
            listOfWorkers.add(worker);
        } else {
            throw new NotTheSmallestException();
        }
    }

    @Override
    public long countLessThanOrganization(Organization organization) {
        long count;
        count = listOfWorkers.stream().filter(worker -> worker.getOrganization() != null).filter(worker -> worker.getOrganization().compareTo(organization) < 0).count();
        return count;
    }

    @Override
    public List<Worker> getAscending() {
        return listOfWorkers.stream().sorted(Comparator.comparing(Worker::getName)).collect(Collectors.toList());
    }

    @Override
    public Double[] getFieldDescendingSalary() {
        Double[] result = new Double[listOfWorkers.size()];
        int i = 0;
        for(Worker w:listOfWorkers){
            result[i] = w.getSalary();
            i++;
        }
        Arrays.sort(result, Collections.reverseOrder());
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

    @Override
    public Collection<Worker> getListOfWorkers() {
        return listOfWorkers;
    }

    @Override
    public boolean collectionIsEmpty() {
        return listOfWorkers.isEmpty();
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
