package network;

import command.Command;
import command.CommandWithArg;
import command.CommandWithOrganizationArg;
import command.CommandWithWorkerArg;
import worker.OrdinaryWorker;
import worker.Organization;
import worker.Worker;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 111113171L;
    Command command;
    Worker worker;
    Organization organization;

    public Request(Command command){
        this.command = command;
        if(command instanceof CommandWithArg){
            if(command instanceof CommandWithOrganizationArg){
                organization = ((CommandWithOrganizationArg) command).getOrganization();
            }
            if(command instanceof CommandWithWorkerArg){
                worker = ((CommandWithWorkerArg) command).getWorker();
            }
        }
    }

    public Command getCommand() {
        return this.command;
    }

    @Override
    public String toString(){
        return command.toString();
    }
}
