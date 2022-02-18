package worker;

import exceptions.InvalidWorkerFieldException;

import java.io.Serializable;

public class OrdinaryCoordinates extends DefaultCoordinates implements Serializable {
    //x не может быть null
    //Максимальное значение y: 241

    public OrdinaryCoordinates(){
        x = 0.0;
        y = 0;
    }

    public OrdinaryCoordinates(Double x, float y){
        if(x != null) {
            this.x = x;
        } else {
            throw new InvalidWorkerFieldException("invalid x coordinate");
        }
        if(y <= 241) {
            this.y = y;
        } else {
            throw new InvalidWorkerFieldException("invalid y coordinate");
        }
    }

    @Override
    public void setX(Double x){
        if(x == null) throw new InvalidWorkerFieldException("this_field_don't_be_null");
        this.x = x;
    }

    @Override
    public void setY(float y){
        if(y > 241) throw new InvalidWorkerFieldException();
        this.y = y;
    }
}
