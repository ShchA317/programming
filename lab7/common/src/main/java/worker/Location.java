package worker;

public interface Location extends Comparable<Location>{
    Double getX(); //Поле не может быть null
    double getY();
    String getName(); //Поле может быть null
    void setX(Double x);
    void setY(double y);
    void setName(String name);

    String toFormalString();
}
