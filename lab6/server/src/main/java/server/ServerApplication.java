package server;
import application.Application;
import command.*;
import command_execution.ScriptReaderImpl;
import server_commands.ExecuteScriptCommand;
import server_commands.ExitCommand;
import server_commands.SaveCommand;
import worker.CollectionOfWorkersManager;
import collection.JSONFileWorkerReader;
import collection.JSONFileWorkerWriter;
import collection.ListOfWorkerManager;
import command_execution.CommandExecutor;
import command_execution.CommandExecutorImpl;
import connection.*;
import exceptions.CommandExecuteException;
import exceptions.CommandExecutionException;
import exceptions.IncorrectFileSettings;
import network.Request;
import network.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Selector;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ServerApplication implements Application{
    public static String dataFileName = System.getenv("DATA_FOR_LAB6");
    boolean isRunning;
    private final RequestReader requestReader = new RequestReaderImpl();
    private final ResponseWriter responseWriter = new ResponseWriterImpl();
    private final ServerConnectionManager serverConnectionManager = new ServerConnectionManagerImpl();
    private final CollectionOfWorkersManager collectionOfWorkersManager = new
                                        ListOfWorkerManager(new JSONFileWorkerReader(dataFileName),
                                                            new JSONFileWorkerWriter(dataFileName));
    private final CommandExecutor commandExecutor = new CommandExecutorImpl(collectionOfWorkersManager);
    private final ResponseSender responseSender = new ResponseSenderImpl();
    private Map<String, Command> serverCommands;

    String address = "localhost";
    int port = 8888;

    public void start(){
        try {
            serverCommands = getServerCommand();
        } catch (IOException ioe){
            log.Logback.getLogger().error("problems with script command");
        }
        consoleStart();
        log.Logback.getLogger().info("server was started");
        isRunning = true;
        Command command;
        if(dataFileName == null){
            throw new IncorrectFileSettings();
        }
        try{
            collectionOfWorkersManager.parseDataToCollection();
            log.Logback.getLogger().info("data was parsed");
        } catch (Exception e){

            log.Logback.getLogger().error("Problems with data file");
            e.printStackTrace();
        }
        Selector selector;
        while(isRunning){
            try {
                serverConnectionManager.openConnection(address, port);
                log.Logback.getLogger().info("connection is open");
                try {
                    selector = serverConnectionManager.listen();
                } catch (ClosedSelectorException e){
                    log.Logback.getLogger().warn("close selector exception");
                    return;
                }
                try {
                    Request request = requestReader.readRequest(selector);
                    log.Logback.getLogger().info("Request was received");
                    command = request.getCommand();
                    log.Logback.getLogger().info("Command was decrypted");
                    String result;
                    try {
                        result = commandExecutor.executeCommand(command);
                    } catch (Exception e){
                        e.printStackTrace();
                        log.Logback.getLogger().error("Error with command execution");
                        result = "error :(";
                    }
                    log.Logback.getLogger().info("Command was executed");
                    Response response = responseWriter.writeResponse(result);
                    log.Logback.getLogger().info("Response was wrote");
                    responseSender.sendResponse(response, selector);
                    log.Logback.getLogger().info("Response was sent");
                    serverConnectionManager.stop();
                    log.Logback.getLogger().info("Client was served");
                } catch (CommandExecuteException | CommandExecutionException e){
                    log.Logback.getLogger().warn("Command execute exception");
                    Response response = new Response(e.getMessage(), false);
                    log.Logback.getLogger().info("Response with error massage was wrote");
                    responseSender.sendResponse(response, selector);
                    log.Logback.getLogger().info("Response was sent");
                    serverConnectionManager.stop();
                    log.Logback.getLogger().info("Client was served");
                } finally {

                }
            } catch (IOException ioe){
                ioe.printStackTrace();
                try{
                    serverConnectionManager.stop();
                } catch (Exception e){
                    isRunning = false;
                }
            } catch (Exception e){
                e.printStackTrace();
                return;
            }
        }
    }

    private void consoleStart(){
        Thread consoleTread = new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while(!Thread.interrupted()){
                try{
                    String[] str = reader.readLine().trim().split("\\s+");
                    Command command = serverCommands.get(str[0].toLowerCase(Locale.ROOT));
                    if(command != null){
                        if(command instanceof ExecuteScriptCommand){
                            ((ExecuteScriptCommand) command).execute(collectionOfWorkersManager, str[1]);
                        } else {
                            command.execute(collectionOfWorkersManager);
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

    public void exit() throws IOException {
        collectionOfWorkersManager.save();
        log.Logback.getLogger().warn("Collection was saved");
        serverConnectionManager.stop();
        isRunning = false;
    }

    private Map<String, Command> getServerCommand() throws IOException {
        Map<String, Command> result = new HashMap<>();
        result.put("save", new SaveCommand());
        result.put("exit", new ExitCommand(this));
        result.put("execute_script", new ExecuteScriptCommand(new ScriptReaderImpl(getScriptCommands())));
        return result;
    }

    private Map<String, Command> getScriptCommands() {
        Map<String, Command> result = new HashMap<>();
        result.put("add", new AddCommand());
        result.put("add_if_min", new AddIfMinCommand());
        result.put("clear", new ClearCommand());
        result.put("remove_by_id", new RemoveByIdCommand());
        result.put("remove_head", new RemoveHeadCommand());
        result.put("update", new UpdateCommand());
        return result;
    }
}
