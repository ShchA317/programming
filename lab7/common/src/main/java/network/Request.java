package network;

import command.Command;
import command.CommandWithArg;
import command.CommandWithOrganizationArg;
import command.CommandWithWorkerArg;
import user.Auth;
import worker.OrdinaryOrganization;
import worker.OrdinaryWorker;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

public class Request implements Serializable {
    private static final long serialVersionUID = 111113171L;
    private final Command command;
    private final Auth auth;
    private transient SocketChannel socketChannel;

    public Request(Command command, Auth auth){
        this.command = command;
        this.auth = auth;
        if(command instanceof CommandWithArg){
            if(command instanceof CommandWithOrganizationArg){
                OrdinaryOrganization organization = (OrdinaryOrganization) ((CommandWithOrganizationArg) command).getOrganization();
            }
            if(command instanceof CommandWithWorkerArg){
                OrdinaryWorker worker = ((CommandWithWorkerArg) command).getWorker();
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

    public Auth getAuth() {
        return auth;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
}
