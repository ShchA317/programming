package main.worker;

import exeptions.InvalidLocationException;

public class OrdinaryLocation extends DefaultLocation{
    //x не может быть null
    //name может быть null
    @Override
    public void setX(Double x){
        if(x == null) throw new InvalidLocationException();
        this.x = x;
    }
}
