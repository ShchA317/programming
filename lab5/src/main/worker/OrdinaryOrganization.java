package main.worker;

import exeptions.InvalidOrganizationException;

public class OrdinaryOrganization extends DefaultOrganization{
    //fullName не может быть пустой, fullName не может быть null
    //annualTurnover может быть null, Значение поля должно быть больше 0
    //officialAddress может быть null
    @Override
    public void setFullName(String fullName){
        if(fullName == null || fullName.length() == 0) throw new InvalidOrganizationException();
        this.fullName = fullName;
    }

    @Override
    public void setAnnualTurnover(Integer annualTurnover){
        if(annualTurnover != null){
            if(fullName.length() == 0) throw new InvalidOrganizationException();
        }
        this.annualTurnover = annualTurnover;
    }
}
