package client_command;

import command.Command;
import io.ClientWriter;
import worker.CollectionOfWorkersManager;

import java.util.ResourceBundle;

public class HelpCommand implements Command {
    ClientWriter clientWriter;
    ResourceBundle bundle;

    public HelpCommand(ClientWriter clientWriter, ResourceBundle bundle){
        this.clientWriter = clientWriter;
        this.bundle = bundle;
    }

    @Override
    public void execute() {
        clientWriter.write(bundle.getString("help_message"));
    }

    /** делает то же, что и execute(), не рекомендуется использовать
     *
     * @param collectionOfWorkersManager с параметром ничего не происходит
     *                                  предпочтительнее использовать метод без параметра
     * передаётся только потому что один ленивый студент не захотел под конец выполнения лабы менять архитектуру наследования команд
     */
    @Override
    public void execute(CollectionOfWorkersManager collectionOfWorkersManager) {
        clientWriter.write(bundle.getString("help_message"));
    }

    /**
     * не рекомендуется использовать
     * существует только потому что один ленивый студент не захотел под конец выполнения лабы менять архитектуру наследования команд
     * @return возвращает пустую строку
     */
    @Override
    public String getResult() {
        return "";
    }
}
