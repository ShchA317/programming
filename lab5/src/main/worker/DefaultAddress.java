package main.worker;

import java.util.Objects;

public abstract class DefaultAddress implements Address{

    protected String street;
    protected String zipCode;
    protected Location town;

    @Override
    public int compareTo(Address address){
        int result = this.town.compareTo(address.getTown());
        if(result == 0){
            result = this.zipCode.compareTo(address.getZipCode());
            if(result == 0){
                result = this.street.compareTo(address.getStreet());
            }
        }
        return result;
    }

    public boolean equals(Address address){
        if(this == address)return true;
        return (this.town.equals(address.getTown())&&
                this.street.equals(address.getStreet())&&
                this.zipCode.equals(address.getZipCode()));
    }

    @Override
    public int hashCode(){
        return Objects.hash(town, zipCode, town);
    }

    @Override
    public String getStreet() {
        return this.street;
    }

    @Override
    public String getZipCode() {
        return this.zipCode;
    }

    @Override
    public Location getTown() {
        return this.town;
    }

    @Override
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public void setTown(Location town) {
        this.town = town;
    }

}
