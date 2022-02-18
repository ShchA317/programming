package server;

import application.Application;
import auth.AuthManager;
import auth.AuthManagerImpl;
import command.*;
import command_execution.CommandExecutor;
import command_execution.CommandExecutorImpl;
import command_execution.ScriptReaderImpl;
import connection.*;
import data.DBManager;
import data.DataManager;
import data.ListOfWorkerManager;
import exceptions.DBException;
import server_commands.ExecuteScriptCommand;
import server_commands.ExitCommand;
import user.Auth;
import worker.CollectionOfWorkersManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class ServerApplication implements Application{
    boolean isRunning;
    private final RequestReader requestReader = new RequestReaderImpl();
    private final ResponseWriter responseWriter = new ResponseWriterImpl();
    private final ServerConnectionManager serverConnectionManager = new ServerConnectionManagerImpl();
    private final AuthManager authManager = new AuthManagerImpl();
    private CollectionOfWorkersManager collectionOfWorkersManager;
    private CommandExecutor commandExecutor;
    private final ResponseSender responseSender = new ResponseSenderImpl();
    private Map<String, Command> serverCommands;

    final String address = "localhost";
    final int port = 8888;

    public void start(){
        serverCommands = getServerCommand();
        log.Logback.getLogger().info("server was started");
        isRunning = true;
        try{
            getDataFromDB();
            log.Logback.getLogger().info("data was parsed");
        } catch (Throwable e){
            log.Logback.getLogger().error("Problems with DB");
            e.printStackTrace();
        }
        communicateWithClient();
    }

    private void communicateWithClient() {
        ServerConnectionManagerImpl2 connectionListener = new ServerConnectionManagerImpl2();
        connectionListener.setSocketAddress(new InetSocketAddress(address, port));
        RequestHandler requestHandler = new RequestHandlerImpl(commandExecutor, responseWriter);
        log.Logback.getLogger().info("connection is open");
        try {
            Server server = new Server(connectionListener, requestReader, requestHandler, responseSender);
            consoleStart();
            new Thread(server).start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getDataFromDB() throws IOException, SQLException, DBException {
        FileInputStream fis = new FileInputStream("/home/alexander/Рабочий стол/programming/lab7/server/src/main/resources/config.properties");
        Properties properties = new Properties();
        properties.load(fis);
        String url = (String) properties.get ("db.url");
        String username = (String) properties.get ("db.username");
        String password = (String) properties.get ("db.password");

        DataManager dataManager = new DBManager(url, username, password);
        authManager.setUsers(dataManager.readUsers());
        collectionOfWorkersManager = new ListOfWorkerManager(dataManager);
        commandExecutor = new CommandExecutorImpl(collectionOfWorkersManager, authManager, dataManager);
    }

    private void consoleStart(){
        Thread consoleTread = new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while(isRunning){
                try{
                    String[] str = reader.readLine().trim().split("\\s+");
                    Command command = serverCommands.get(str[0].toLowerCase(Locale.ROOT));
                    if(command != null){
                        if(command instanceof ExecuteScriptCommand){
                            ((ExecuteScriptCommand) command).execute(collectionOfWorkersManager, str[1]);
                        } else {
                            Auth auth = new Auth("server", "Fk4Dl3igjeriehioefjlajie" );
                            command.execute(collectionOfWorkersManager, auth);
                        }
                    } else {
                        log.Logback.getLogger().error("unknowing command");
                    }
                } catch (IOException ioe){
                    log.Logback.getLogger().error("ioe");

                }
            }
        });
        consoleTread.setDaemon(true);
        consoleTread.start();
    }

    public void exit() {
        log.Logback.getLogger().warn("Collection was saved");
        isRunning = false;
    }

    private Map<String, Command> getServerCommand() {
        Map<String, Command> result = new HashMap<>();
        result.put("exit", new ExitCommand(this));
        result.put("execute_script", new ExecuteScriptCommand(new ScriptReaderImpl(getScriptCommands())));
        return result;
    }

    private Map<String, Command> getScriptCommands() {
        Map<String, Command> result = new HashMap<>();
        result.put("add", new AddCommand());
        result.put("add_if_min", new AddIfMinCommand());
        result.put("remove_by_id", new RemoveByIdCommand());
        result.put("remove_head", new RemoveHeadCommand());
        result.put("update", new UpdateCommand());
        return result;
    }
}
