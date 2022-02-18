package worker;

import exceptions.InvalidWorkerFieldException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * конкретная реализация абстрактного работяги
 * обычный работяга
 */
public class OrdinaryWorker extends DefaultWorker implements Serializable {
    //id уникальный и генерится автоматически
    //name не может быть null, Строка не может быть пустой
    //coordinates не может быть null
    //creationDate не может быть null, Значение этого поля должно генерироваться автоматически
    //salary не может быть null, Значение поля должно быть больше 0
    //endDate может быть null
    //position не может быть null
    //status не может быть null
    //organization может быть null

    private static long lastId;
    public static final SortedSet<Long> ids = new TreeSet<>();

    public OrdinaryWorker(Worker worker) {
        this.setName(worker.getName());
        this.setSalary(worker.getSalary());
        this.setCoordinates(worker.getCoordinates());
        this.setPosition(worker.getPosition());
        this.setStatus(worker.getStatus());
        this.setCreationDate(worker.getCreationDate());
        this.setEndDate(worker.getEndDate());
        this.setOrganization(worker.getOrganization());
        this.setId(Long.parseLong("-1"));
    }

    public static long getNewId(){
        lastId++;
        ids.add(lastId);
        return lastId;
    }

     public OrdinaryWorker() {
         while (ids.contains(lastId)) {
             ++lastId;
         }
         setId(lastId);
         this.creationDate = LocalDateTime.now();
         this.name = "default";
         this.coordinates = new OrdinaryCoordinates();
         this.salary = 1.0;
         this.position = Position.MANAGER;
         this.status = Status.FIRED;
         this.endDate = null;
         this.organization = null;
     }

    public OrdinaryWorker(String name, OrdinaryCoordinates coordinates, Double salary,
                          ZonedDateTime endDate, Position position,
                          Status status, Organization organization){

        Worker constructWorker = new OrdinaryWorker();

        constructWorker.setName(name);
        constructWorker.setCoordinates(coordinates);
        constructWorker.setSalary(salary);
        constructWorker.setEndDate(endDate);
        constructWorker.setPosition(position);
        constructWorker.setStatus(status);
        constructWorker.setOrganization(organization);

        this.name = constructWorker.getName();
        this.coordinates = constructWorker.getCoordinates();
        this.salary = constructWorker.getSalary();
        this.endDate = constructWorker.getEndDate();
        this.position = constructWorker.getPosition();
        this.status = constructWorker.getStatus();
        this.organization = constructWorker.getOrganization();

        this.setId(Long.parseLong("-1"));
    }

    public static void removeIdFromSet(Long id) {
        if(ids != null)
            ids.remove(id);
    }

    public static void setNewId(OrdinaryWorker ordinaryWorker){
         ordinaryWorker.setId(lastId);
    }

    @Override
    public void setName(String name){
        if (name == null || (name.length() <= 0)) {
            throw new InvalidWorkerFieldException();
        }
        this.name = name;
    }

    @Override
    public void setCoordinates(Coordinates coordinates){
        if(coordinates == null) throw new InvalidWorkerFieldException();
        this.coordinates = (OrdinaryCoordinates) coordinates;
    }

    @Override
    public void setSalary(Double salary){
        if(salary == null || salary <= 0) throw new InvalidWorkerFieldException("Incorrect salary");
        this.salary = salary;
    }

    @Override
    public void setPosition(Position position){
        if(position == null){
            throw new InvalidWorkerFieldException();
        }
        this.position = position;
    }

    @Override
    public void setStatus(Status status){
        if(status == null) {
            throw new InvalidWorkerFieldException();
        }
        this.status = status;
    }

    @Override
    public void setId(Long id){
        this.id = id;
        ids.add(id);
    }

    @Override
    public String toFormalString() {
        Worker worker = this;
        String result;
        result = "Type: worker" + "\n" +
                "id: " + worker.getId() + "\n" +
                "name: " + worker.getName() + "\n"+
                "coordinates: " + getCoordinatesString() + "\n" +
                "creation date: " + worker.getCreationDate() + "\n" +
                "salary: " + worker.getSalary() + "\n" +
                "end date: " + getEndDateString() + "\n" +
                "position: " + worker.getPosition() + "\n" +
                "status: " + worker.getStatus() + "\n" +
                "organization: " + getOrganizationString();

        return result;
    }

    private String getCoordinatesString(){
         if(coordinates == null) return null;
         return "X: " + coordinates.getX() + " Y: " + coordinates.getY();
    }

    private String getEndDateString() {
        if(endDate == null){
            return "no date specified";
        } else {
            return endDate.toString();
        }
    }

    private String getOrganizationString(){
        if(organization == null){
            return "no organization specified";
        } else {
            return organization.toFormalString();
        }
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Worker){
            if (name.equals(((Worker) o).getName())){
                return salary.equals(((Worker) o).getSalary());
            }
        }
        return false;
    }
}
