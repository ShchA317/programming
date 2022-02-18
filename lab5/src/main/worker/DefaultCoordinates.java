package main.worker;

import java.util.Objects;

public abstract class DefaultCoordinates implements Coordinates{
    protected Double x;
    protected float y;

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setX(Double x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public int compareTo(Coordinates coordinates) {
        int result = this.x.compareTo(coordinates.getX());
        if(result == 0){
            Float thisCoordinatesFloat = this.y;
            result = thisCoordinatesFloat.compareTo(coordinates.getY());
        }
        return result;
    }

    public boolean equals(Coordinates coordinates){
        if(this == coordinates) return true;
        if(coordinates == null) return false;
        return this.x.equals(coordinates.getX())&&
                this.y == coordinates.getY();
    }

    @Override
    public int hashCode(){
        return Objects.hash(y,x);
    }
}
