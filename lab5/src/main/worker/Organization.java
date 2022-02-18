package main.worker;

public interface Organization extends Comparable<Organization>{
    String getFullName(); //Строка не может быть пустой, Поле не может быть null
    Integer getAnnualTurnover(); //Поле может быть null, Значение поля должно быть больше 0
    Address getOfficialAddress(); //Поле может быть null
    void setFullName(String fullName);
    void setAnnualTurnover(Integer annualTurnover);
    void setOfficialAddress(Address officialAddress);
}
