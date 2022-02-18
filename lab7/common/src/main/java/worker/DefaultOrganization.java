package worker;

import java.io.Serializable;
import java.util.Objects;

public abstract class DefaultOrganization implements Organization, Serializable {
    protected String fullName;
    protected Integer annualTurnover;
    protected Address officialAddress;
    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public Integer getAnnualTurnover() {
        return annualTurnover;
    }

    @Override
    public Address getOfficialAddress() {
        return officialAddress;
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public void setAnnualTurnover(Integer annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    @Override
    public void setOfficialAddress(Address officialAddress) {
        this.officialAddress = officialAddress;
    }

    @Override
    public String toFormalString() {
        String result;
        result = "Full name: " + fullName + "\n" +
                "Annual turnover: " + String.valueOf(annualTurnover) + "\n" +
                "Official address: " + officialAddress.toFormalString();
        return result;
    }

    @Override
    public int compareTo(Organization organization) {
        int result = fullName.compareTo(organization.getFullName());
        if(result == 0){
            result = annualTurnover.compareTo(organization.getAnnualTurnover());
            if(result == 0){
                result = officialAddress.compareTo(organization.getOfficialAddress());
            }
        }
        return result;
    }

    public boolean equals(Organization organization){
        if(organization == null) return false;
        if(this == organization) return true;
        return this.fullName.equals(organization.getFullName())&&
                this.annualTurnover.equals(organization.getAnnualTurnover());
    }

    @Override
    public int hashCode(){
        return Objects.hash(fullName, annualTurnover);
    }
}
