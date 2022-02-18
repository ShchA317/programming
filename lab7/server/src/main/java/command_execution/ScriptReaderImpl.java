package command_execution;

import command.*;
import exceptions.InvalidWorkerFieldException;
import exceptions.UnknownCommandException;
import worker.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class ScriptReaderImpl implements ScriptReader{
    private final Map<String, Command> commandMap;
    LinkedList<String> script;

    public ScriptReaderImpl(Map<String, Command> commandMap){
        this.commandMap = commandMap;
    }

    @Override
    public List<Command> getScript(String scriptFileName) throws IOException {
        List<Command> commands = new LinkedList<>();
        script = readFile(scriptFileName);
        while (true) {
            try {
                commands.add(getNextCommand());
            } catch (NoSuchElementException noSuchElementException){
                break;
            }
        }
        return commands;
    }

    @Override
    public Command getNextCommand() {
        String str;
        do {
            str = script.removeFirst().toLowerCase(Locale.ROOT);
        } while (str.equals(""));

        String[] words = str.split("\\s+");
        Command command =  commandMap.get(words[0]);
        if (command != null) {
            if (command instanceof SimpleCommandWithArg) {
                command = ((SimpleCommandWithArg) command).clone();
                ((SimpleCommandWithArg) command).setSimpleArg(words[1]);
            }
            if (command instanceof CommandWithArg) {
                if (command instanceof CommandWithWorkerArg) {
                    ((CommandWithWorkerArg) command).setArg(readWorkerFromThisLine());
                }
                if (command instanceof CommandWithOrganizationArg) {
                    ((CommandWithOrganizationArg) command).setArg(readOrganizationFromThisLine());
                }
            }
        } else {
            throw new UnknownCommandException(str);
        }

        return command;
    }

    private LinkedList<String> readFile(String scriptFileName) throws IOException {
        LinkedList<String> result = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader(scriptFileName));

        String line = reader.readLine();
        while(line != null) {
            result.add(line.trim());
            line = reader.readLine();
        }

        return result;
    }

    private Organization readOrganizationFromThisLine() {
        Organization organization = new OrdinaryOrganization();
        String fullName = script.removeFirst();
        if (!fullName.isEmpty()) {
            organization.setFullName(fullName);
            Integer annualTurnover = Integer.valueOf(script.removeFirst());
            organization.setAnnualTurnover(annualTurnover);
            organization.setOfficialAddress(readOfficialAddressFromTisString());
        }
        return organization;
    }

    private Address readOfficialAddressFromTisString() {
        Address address = new OrdinaryAddress();
        address.setStreet(script.removeFirst());
        address.setZipCode(script.removeFirst());
        address.setTown(readTownFromThisString());
        return address;
    }

    private Location readTownFromThisString() {
        Location town = new OrdinaryLocation();
        String townName = script.removeFirst();
        if (townName.length() == 0){
            return null;
        }
        town.setName(townName);
        Double x = new Double(script.removeFirst());
        town.setX(x);
        Double y = new Double(script.removeFirst());
        town.setY(y);
        return town;
    }

    private OrdinaryWorker readWorkerFromThisLine() {
            OrdinaryWorker result = new OrdinaryWorker();
            result.setName(script.removeFirst());
            Coordinates coordinates = new OrdinaryCoordinates();
            coordinates.setX(Double.parseDouble(script.removeFirst()));
            coordinates.setY(Float.parseFloat(script.removeFirst()));
            result.setCoordinates(coordinates);
            result.setSalary(Double.parseDouble(script.removeFirst()));
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String newEndDate = script.removeFirst();
            if (newEndDate.length() == 0) {
                result.setEndDate(null);
            } else {
                Date endDate1 = null;
                try {
                    endDate1 = df.parse(newEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ZonedDateTime endDate = ZonedDateTime.ofInstant(endDate1.toInstant(), ZoneId.systemDefault());
                result.setEndDate(endDate);
            }
            result.setPosition(parsePosition(script.removeFirst()));
            result.setStatus(parseStatus(script.removeFirst()));
            result.setOrganization(readOrganizationFromThisLine());
            return result;
        }

    private Position parsePosition(String s){
        Position p;
        String position = s.toLowerCase(Locale.ROOT);
        position = position.toLowerCase();
        if (position.matches("lead\\s+developer") || position.matches("lead_developer")) {
            p = Position.LEAD_DEVELOPER;
        } else {
            switch (position) {
                case "manager":
                    p = Position.MANAGER;
                    break;
                case "engineer":
                    p = Position.ENGINEER;
                    break;
                case "developer":
                    p = Position.DEVELOPER;
                    break;
                case "baker":
                    p = Position.BAKER;
                    break;
                default:
                    throw new InvalidWorkerFieldException("invalid_position");
            }
        }
        return p;
    }

    private Status parseStatus(String string) {
        Status s;
        if(string.toLowerCase(Locale.ROOT).matches("recommended\\s+for\\s+promotion") || string.matches("recommended_for_promotion")){
            s = Status.RECOMMENDED_FOR_PROMOTION;
        }
        else {
            switch (string.toLowerCase()) {

                case "fired":
                    s = Status.FIRED;
                    break;
                case "probation":
                    s = Status.PROBATION;
                    break;
                default:
                    throw new InvalidWorkerFieldException("invalid_status");
            }
        }
        return s;
    }
}
