package main.worker;

public interface Worker extends Comparable<Worker>{
    Long getId();
    String getName();
    Coordinates getCoordinates();
    java.time.LocalDateTime getCreationDate();
    Double getSalary();
    java.time.ZonedDateTime getEndDate();
    Position getPosition();
    Status getStatus();
    Organization getOrganization();

    void setId(Long id);
    void setName(String name);
    void setCoordinates(Coordinates coordinates);
    void setCreationDate(java.time.LocalDateTime creationDate);
    void setSalary(Double salary);
    void setEndDate(java.time.ZonedDateTime endDate);
    void setPosition(Position position);
    void setStatus(Status status);
    void setOrganization(Organization organization);
}