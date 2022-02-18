package main.worker;

import java.util.Objects;

public abstract class DefaultLocation implements Location{
    protected Double x;
    protected double y;
    protected String name;

    @Override
    public int compareTo(Location location) {
        int result = this.name.compareTo(location.getName());
        if(result == 0){
            result = this.x.compareTo(location.getX());
            if(result == 0){
                Double yD = this.y;
                result = yD.compareTo(location.getY());
            }
        }
        return result;
    }

    public boolean equals(Location location){
        if (this == location) return true;
        if (location == null || this.getClass() != location.getClass()) return false;
        return (this.x.equals(location.getX()) &&
                this.y == location.getY() &&
                this.name.equals(location.getName()));
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.x, this.y, this.name);
    }

    @Override
    public String toString(){
        return this.name + ' ' + x + ' ' + y;
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setX(Double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}