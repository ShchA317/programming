package command;

import worker.Organization;

import java.io.Serializable;

public abstract class CommandWithOrganizationArg implements CommandWithArg, Serializable {
    Organization organization;
    public void setArg(Organization organization){
        this.organization = organization;
    }
    public Organization getOrganization(){
        return organization;
    }
    @Override
    public void execute() {

    }
}
