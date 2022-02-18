package worker;

import exceptions.InvalidWorkerFieldException;

import java.io.Serializable;

public class OrdinaryAddress extends DefaultAddress implements Serializable {

    //street может быть null
    //zipCode не может быть null
    //town не может быть null

    @Override
    public void setZipCode(String zipCode) {
        if(zipCode == null) throw new InvalidWorkerFieldException();
        this.zipCode = zipCode;
    }

    @Override
    public void setTown(Location town){
        if(town == null) throw new InvalidWorkerFieldException();
        this.town = town;
    }

    @Override
    public String toFormalString() {
        String streetString, zipCodeString, townString;
        if(street == null){
            streetString = "there is no street";
        } else {
            streetString = street;
        }
        if (town == null){
            townString = "there is no town";
        } else {
            townString = town.toFormalString();
        }
        zipCodeString = zipCode;

        return "Street: " + streetString + "\n" +
                "Zip code: " + zipCodeString + "\n" +
                "Town: " + townString;
    }
}
