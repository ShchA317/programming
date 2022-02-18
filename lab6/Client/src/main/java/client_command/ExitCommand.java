package client_command;
import application.Application;
import command.Command;
import worker.CollectionOfWorkersManager;

import java.io.IOException;

public class ExitCommand implements Command {
    Application application;
    public ExitCommand(Application application){
        this.application = application;
    }

    public void execute() throws IOException {
        application.exit();
    }

    /**
     *
     * @param collectionOfWorkersManager с параметром ничего не происходит
     *                                  предпочтительнее использовать метод без параметра
     * передаётся только потому что один ленивый студент не захотел под конец выполнения лабы менять архитектуру наследования команд
     */
    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager) throws IOException {
        application.exit();
    }

    @Override
    public String getResult() {
        return null;
    }
}
