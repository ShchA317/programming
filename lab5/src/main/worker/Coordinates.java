package main.worker;

public interface Coordinates extends Comparable<Coordinates>{
    Double getX(); //Поле не может быть null
    float getY(); //Максимальное значение поля: 241
    void setX(Double x);
    void setY(float y);
}

