package main.worker;

import exeptions.InvalidCoordinatesException;

public class OrdinaryCoordinates extends DefaultCoordinates {
    //x не может быть null
    //Максимальное значение y: 241

    @Override
    public void setX(Double x){
        if(x == null) throw new InvalidCoordinatesException();
        this.x = x;
    }

    @Override
    public void setY(float y){
        if(y > 241) throw new InvalidCoordinatesException();
        this.y = y;
    }
}
