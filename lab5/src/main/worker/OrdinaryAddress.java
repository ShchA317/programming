package main.worker;

import exeptions.InvalidAddressException;

public class OrdinaryAddress extends DefaultAddress {

    //street может быть null
    //zipCode не может быть null
    //town не может быть null

    @Override
    public void setZipCode(String zipCode) {
        if(zipCode == null) throw new InvalidAddressException();
        this.zipCode = zipCode;
    }

    @Override
    public void setTown(Location town){
        if(town == null) throw new InvalidAddressException();
        this.town = town;
    }
}
