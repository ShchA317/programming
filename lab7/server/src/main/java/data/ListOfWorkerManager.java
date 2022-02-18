package data;

import exceptions.DBException;
import exceptions.NoSuchWorkerException;
import exceptions.NotTheSmallestException;
import user.Auth;
import worker.CollectionOfWorkersManager;
import worker.OrdinaryWorker;
import worker.Organization;
import worker.Worker;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * concrete implementation worker.CollectionOfWorkersManager for LinkedList-collection
 */
public class ListOfWorkerManager implements CollectionOfWorkersManager {
    protected final ZonedDateTime creationDate;
    protected LinkedList<Worker> listOfWorkers;
    protected LinkedList<Worker> bufferListOfWorkers = new LinkedList<>();
    protected final DataManager dataManager;

    public ListOfWorkerManager(DataManager dataManager){
        creationDate = ZonedDateTime.now();
        this.dataManager = dataManager;
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
        } catch (Exception | DBException e){
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
    }

    @Override
    public void setCollection(Collection<Worker> collection) {
        listOfWorkers = (LinkedList<Worker>) collection;
    }

    @Override
    public Worker getWorkerByNumber(int number) {
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
        } catch (DBException e) {
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
        return listOfWorkers.get(number);
    }

    @Override
    public void addWorker(Worker worker, Auth auth) {
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
        } catch (DBException e) {
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
        worker.setId(OrdinaryWorker.getNewId());
        try {
            dataManager.addElement(worker, auth);
            listOfWorkers.add(worker);
        } catch (DBException e) {
            e.printStackTrace();

        }
    }

    @Override
    public String getInfo() {
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
        } catch (DBException e) {
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
        return "Collection: linked list of workers " +
                "\nCreation date: " + creationDate.format(format) +
                "\nNumber of elements: " + (long) listOfWorkers.size();
    }

    @Override
    public void updateWorkerById(long id, Worker newWorker, Auth auth) {
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
        } catch (DBException e) {
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
        int index = listOfWorkers.indexOf(getWorkerByID(id));
        try {
            dataManager.updateElement(newWorker, id, auth);
            listOfWorkers.remove(index);
            listOfWorkers.add(index, newWorker);
        } catch (DBException e) {
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
    }

    @Override
    public void removeById(long id, Auth auth) {
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
        } catch (DBException e) {
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
        try {
            dataManager.removeElement(id, auth);
        } catch (DBException e) {
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
        listOfWorkers.remove(getWorkerByID(id));
    }

    @Override
    public Worker getHead() {
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
        } catch (DBException e) {
            log.Logback.getLogger().error(e.getMessage());
        }
        return listOfWorkers.getFirst();
    }

    @Override
    public Worker removeHead(Auth auth) {
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
            long headsId = listOfWorkers.getFirst().getId();
            dataManager.removeElement((int)headsId, auth);
        } catch (DBException e) {
            log.Logback.getLogger().error(e.getMessage());
            throw new NoSuchWorkerException();
        }
        OrdinaryWorker worker = (OrdinaryWorker) listOfWorkers.getFirst();
        OrdinaryWorker.removeIdFromSet(worker.getId());
        return listOfWorkers.remove();
    }

    @Override
    public void addIfMin(Worker worker, Auth auth) {
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
        } catch (DBException e) {
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
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
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
        } catch (DBException e) {
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
        long count;
        count = listOfWorkers.stream().filter(worker -> worker.getOrganization() != null)
                .filter(worker -> worker.getOrganization().compareTo(organization) < 0).count();
        return count;
    }

    @Override
    public List<Worker> getAscending() {
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
        } catch (DBException e) {
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
        return listOfWorkers.stream().sorted(Comparator.comparing(Worker::getName))
                .collect(Collectors.toList());
    }

    @Override
    public Double[] getFieldDescendingSalary() {
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
        } catch (DBException e) {
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
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
    public int getSize() {
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
        } catch (DBException e) {
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
        return listOfWorkers.size();
    }

    @Override
    public void saveBeforeDangerActions() {
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
        } catch (DBException e) {
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
        bufferListOfWorkers = this.listOfWorkers;
    }

    @Override
    public Collection<Worker> getListOfWorkers() {
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
        } catch (DBException e) {
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
        return listOfWorkers;
    }

    @Override
    public boolean collectionIsEmpty() {
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
        } catch (DBException e) {
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
        return listOfWorkers.isEmpty();
    }

    public Worker getWorkerByID(long id){
        try {
            listOfWorkers = (LinkedList<Worker>) dataManager.readCollection();
        } catch (DBException e) {
            e.printStackTrace();
            log.Logback.getLogger().error(e.getMessage());
        }
        System.out.println(id);
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
