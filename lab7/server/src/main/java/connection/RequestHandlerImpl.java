package connection;

import command.Command;
import command.UsersCommand;
import command_execution.CommandExecutor;
import network.Request;
import network.Response;

public class RequestHandlerImpl implements RequestHandler {
    private final CommandExecutor commandExecutor;
    private final ResponseWriter responseWriter;

    public RequestHandlerImpl(CommandExecutor commandExecutor, ResponseWriter responseWriter) {
        this.commandExecutor = commandExecutor;
        this.responseWriter = responseWriter;
    }

    @Override
    public Response handleRequest(Request request) {
        final Command command;


        String user;
        if(request.getAuth() != null) {
            user = request.getAuth().getLogin();
        } else {
            user = "null user";
        }
        log.Logback.getLogger().info("Request was received from " + user);



        command = request.getCommand();
        log.Logback.getLogger().info("Command was decrypted");
        String result;
        try {
            result = commandExecutor.executeCommand(command, request.getAuth());
        } catch (Exception e){
            e.printStackTrace();
            log.Logback.getLogger().error("Error with command execution");
            result = "error :(";
        }
        log.Logback.getLogger().info("Command was executed");


        Response response;
        if(command instanceof UsersCommand){
            response = responseWriter.writeResponse(result, ((UsersCommand) command).isSuccess());
        } else {
            response = responseWriter.writeResponse(result);
        }
        log.Logback.getLogger().info("Response was wrote");

        return response;
    }
}
