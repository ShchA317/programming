package main.worker;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * абстрактный класс, реализующий интерфейс работяга
 * дефолтный работяга
 */
public abstract class DefaultWorker implements Worker{
    protected Long id;
    protected String name;
    protected OrdinaryCoordinates coordinates;
    protected java.time.LocalDateTime creationDate;
    protected Double salary;
    protected java.time.ZonedDateTime endDate;
    protected Position position;
    protected Status status;
    protected Organization organization;

    @Override
    public int compareTo(Worker worker){
        return this.name.compareTo(worker.getName());
    }

    public boolean equals(Worker worker){
        if(worker == null) return false;
        if(this == worker) return true;
        return this.name.equals(worker.getName()) &&
                this.coordinates.equals(worker.getCoordinates());
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, coordinates);
    }

    @Override
    public String toString(){
        return "Id:" + getId().toString() + "Name:" + getName();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public Double getSalary() {
        return salary;
    }

    @Override
    public ZonedDateTime getEndDate() {
        return endDate;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public Organization getOrganization() {
        return organization;
    }

    @Override
    public void setId(Long id){
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = (OrdinaryCoordinates) coordinates;
    }

    @Override
    public void setCreationDate(java.time.LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
