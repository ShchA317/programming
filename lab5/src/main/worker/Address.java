package main.worker;


/**
 * Interface for address
 */
public interface Address extends Comparable<Address>{
    /**
     * method for getting street
     * @return street
     */
    String getStreet();

    /**
     * method for getting zipcode
     * @return zipcode
     */
    String getZipCode();

    /**
     * method for getting town
     * @return town of Location
     */
    Location getTown();

    /**
     * method for setting street
     */
    void setStreet(String street);

    /**
     * method for setting zipcode
     */
    void setZipCode(String zipCode);

    /**
     * method for setting town
     */
    void setTown(Location town);
}
