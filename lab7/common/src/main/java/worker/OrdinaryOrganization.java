package worker;

import exceptions.InvalidWorkerFieldException;

import java.io.Serializable;

public class OrdinaryOrganization extends DefaultOrganization implements Serializable {
    //fullName не может быть пустой, fullName не может быть null
    //annualTurnover может быть null, Значение поля должно быть больше 0
    //officialAddress может быть null
    public OrdinaryOrganization(){
        this.setFullName("DefaultFullName");
        this.setAnnualTurnover(100);
        this.setOfficialAddress(new OrdinaryAddress());
    }

    @Override
    public void setFullName(String fullName){
        if(fullName == null || fullName.length() == 0) throw new InvalidWorkerFieldException();
        this.fullName = fullName;
    }

    @Override
    public void setAnnualTurnover(Integer annualTurnover){
        if(annualTurnover != null){
            if(fullName.length() == 0) throw new InvalidWorkerFieldException();
        }
        this.annualTurnover = annualTurnover;
    }
}
