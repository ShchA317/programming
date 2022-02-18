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
import user.Auth;

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
    private Command command;
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag("ru-RU"));
    private final ClientWriter clientWriter = new ConsoleWriter();
    private final Map<String, Command> commands = getCommands();
    private final CommandReader consoleReader = new ConsoleCommandReader(clientWriter, commands, bundle);
    private final ConnectionManager connectionManager = new ConnectionManagerImpl();
    private final RequestWriter requestWriter = new OrdinaryRequestWriter();
    private final RequestSender requestSender = new OrdinaryRequestSender();
    private final ResponseReader responseReader = new ResponseReaderIml();
    private boolean isLogin;
    private Auth auth;

    public ClientApplication(String address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void start(){
        isRunning = true;
        isLogin = false;
        clientWriter.write(bundle.getString("welcome"));
        while(!isLogin) {
            try {
                logIn();
            } catch (Exception e){
                clientWriter.write("что-то пошло не так");
            }
        }
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
            Request request = requestWriter.writeRequest(command, auth);
            requestSender.initOutputStream(socketChannel);
            requestSender.sendRequest(request, socketChannel);
            Response response = responseReader.getResponse(socketChannel);
            clientWriter.write(response.getMessage());
            socketChannel.close();
        } catch (IOException ioe){
            System.err.println(bundle.getString("problems_with_connection_message"));
        }
    }

    private void logIn() throws IOException {
        command = consoleReader.readCommand();
        if(command instanceof UsersCommand){
            Response response = authorizeRequest();
            assert response != null;
            clientWriter.write(response.getMessage());
            if (response.getSuccess()) {
                isLogin = true;
                auth = new Auth(((UsersCommand) command).getLogin(), ((UsersCommand) command).getPassword());
            }
        } else {
            clientWriter.write("Да не так");
        }
    }

    private Response authorizeRequest() {
        try {
            SocketChannel socketChannel = connectionManager.openConnection(address, port);
            Auth auth = new Auth("auth request", "request");
            Request request = requestWriter.writeRequest(command, auth);
            requestSender.initOutputStream(socketChannel);
            requestSender.sendRequest(request, socketChannel);
            Response response = responseReader.getResponse(socketChannel);
            socketChannel.close();
            return response;
        } catch (IOException | ClassNotFoundException ioe){
            System.err.println(bundle.getString("problems_with_connection_message"));
            return null;
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
        commands.put("registration", new RegistrationCommand());
        commands.put("authorization", new AuthorizationCommand());
        return commands;
    }
}
