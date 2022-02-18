package worker;

import exceptions.InvalidWorkerFieldException;

import java.io.Serializable;

public class OrdinaryLocation extends DefaultLocation implements Serializable {
    //x не может быть null
    //name может быть null
    @Override
    public void setX(Double x){
        if(x == null) throw new InvalidWorkerFieldException();
        this.x = x;
    }

    @Override
    public String toFormalString() {
        String xString = x.toString();
        String nameString;
        if(name == null){
            nameString = "there is no name";
        } else {
            nameString = name;
        }
        return "x: " + xString + "\n" +
                "name:" + nameString;
    }
}
