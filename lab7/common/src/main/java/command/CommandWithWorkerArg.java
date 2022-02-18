package command;

import worker.OrdinaryWorker;

import java.io.Serializable;

public abstract class CommandWithWorkerArg implements CommandWithArg, Serializable, Cloneable {
    protected OrdinaryWorker worker;

    public void setArg(OrdinaryWorker worker) {
        this.worker = worker;
    }
    public OrdinaryWorker getWorker(){
        return worker;
    }

    @Override
    public String toString(){
        return "commandWithWorkerArg" + worker.toFormalString();
    }


    @Override
    public CommandWithWorkerArg clone() {
        try {
            return (CommandWithWorkerArg) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
