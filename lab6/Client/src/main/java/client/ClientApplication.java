package client;

import client_command.ExitCommand;
import client_command.HelpCommand;
import command.*;
import connection.*;
import network.Request;
import network.Response;
import application.Application;
import exceptions.UnknownCommandException;
import io.CommandReader;
import io.ConsoleCommandReader;
import io.ClientWriter;
import io.ConsoleWriter;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ClientApplication implements Application {
    private boolean isRunning;
    private final String address;
    private final int port;
    Command command;
    ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag("ru-RU"));
    ClientWriter clientWriter = new ConsoleWriter();
    Map<String, Command> commands = getCommands();
    CommandReader consoleReader = new ConsoleCommandReader(clientWriter, commands, bundle);
    ConnectionManager connectionManager = new ConnectionManagerImpl();
    RequestWriter requestWriter = new OrdinaryRequestWriter();
    RequestSender requestSender = new OrdinaryRequestSender();
    ResponseReader responseReader = new ResponseReaderIml();

    public ClientApplication(String address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void start(){
        isRunning = true;
        clientWriter.write(bundle.getString("welcome"));
        while(isRunning){
            try {
                working();
            } catch (UnknownCommandException uce){
                clientWriter.write(bundle.getString("try_again_message"));
            } catch (IOException ioe){
                clientWriter.write("Problems with input and output in program");
            } catch (ClassNotFoundException e) {
                System.out.println("Problems with deserialization");
                e.printStackTrace();
            }
        }
    }

    private void working() throws IOException, ClassNotFoundException {
        command = consoleReader.readCommand();
        if(command instanceof ExitCommand || command instanceof HelpCommand){
            command.execute();
        } else {
            communicateWithServer(command);
        }
    }

    private void communicateWithServer(Command command) throws ClassNotFoundException {
        try {
            SocketChannel socketChannel = connectionManager.openConnection(address, port);
            Request request = requestWriter.writeRequest(command);
            requestSender.initOutputStream(socketChannel);
            requestSender.sendRequest(request, socketChannel);
            Response response = responseReader.getResponse(socketChannel);
            clientWriter.write(response.getMessage());
            socketChannel.close();
        } catch (IOException ioe){
            System.err.println(bundle.getString("problems_with_connection_message"));
        }
    }

    @Override
    public void exit() {
        isRunning = false;
    }

    Map<String, Command> getCommands(){
        HashMap<String, Command> commands = new HashMap<>();
        commands.put("add", new AddCommand());
        commands.put("add_if_min", new AddIfMinCommand());
        commands.put("clear", new ClearCommand());
        commands.put("count_less_than_organization", new CountLessThanOrganizationCommand());
        commands.put("exit", new ExitCommand(this));
        commands.put("head", new HeadCommand());
        commands.put("help", new HelpCommand(clientWriter, bundle));
        commands.put("info", new InfoCommand());
        commands.put("print_ascending", new PrintAscendingCommand());
        commands.put("print_field_descending_salary", new PrintFieldDescendingSalaryCommand());
        commands.put("remove_by_id", new RemoveByIdCommand());
        commands.put("remove_head", new RemoveHeadCommand());
        commands.put("show", new ShowCommand());
        commands.put("update", new UpdateCommand());
        return commands;
    }
}
