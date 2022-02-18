package main;

import exeptions.*;
import main.worker.*;
import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

/**
 * класс для чтения и исполнения пользовательских скриптов
 */
public class ScriptReader extends AbstractCommandReader{
private LinkedList<String> script = new LinkedList<>();
    public ScriptReader(String inputFileName, CollectionOfWorkerManager collectionOfWorkerManager,
                        Messages messages, Messenger messenger, CommandFactory commandFactory) {
        super(collectionOfWorkerManager, messages, messenger, commandFactory);
        collectionOfWorkerManager.saveBeforeDangerActions();
        try {
            reader = new BufferedReader(new FileReader(inputFileName));
            String line = reader.readLine();
            while(line != null) {
                script.add(line);
                line = reader.readLine().trim();
            }
        } catch (NullPointerException e){

        }
        catch (Exception e){
            System.err.println("problems with script");
        }
    }

    @Override
    public void readCommands() {

        try {
            String line;
            while(!script.isEmpty()){
                line = script.remove();
                String[] command = line.trim().split("\\s++");
                decodeAndTryExecuteCommand(command);
            }
        }catch (ScriptFileRecursionException e){
            System.err.println("Script recursion");
            collectionOfWorkerManager.recover();
        }
        catch (Exception e){
            collectionOfWorkerManager.recover();
            messenger.output("collection was be recovered");
        }
    }

    @Override
    public void exit() {
        isRunning = false;
    }

    @Override
    public Worker readWorker() {
        try {
            Worker worker = new OrdinaryWorker();
            curSetName(worker);
            curSetCoordinates(worker);
            curSetSalary(worker);
            curSetEndDate(worker);
            curSetPosition(worker);
            curSetStatus(worker);
            curSetOrganization(worker);
            return worker;
        } catch (InvalidWorkerFieldException e){
            throw new IllegalScriptException();
        }
    }

    @Override
    public Organization readOrganization() {
        return null;
    }

    private void curSetName(Worker worker) {
            String name = script.remove();
            worker.setName(name);
    }

    private void curSetCoordinates(Worker worker) {
        Coordinates coordinates = new OrdinaryCoordinates();
        String curX = script.remove();
        if (curX.isEmpty()) {
            throw new InvalidCoordinatesException();
        }
        Double x = new Double(curX);
        coordinates.setX(x);
        String curY = script.remove();
        if (curY.isEmpty()) {
            throw new InvalidCoordinatesException();
        }
        float y = new Float(curY);
        coordinates.setY(y);
        worker.setCoordinates(coordinates);
    }


    private void curSetSalary(Worker worker) {
        String curSalary = script.remove();
        if(curSalary.isEmpty()){
            throw new InvalidWorkerFieldException();
        }Double salary = new Double(curSalary);
        worker.setSalary(salary);
    }

    private void curSetStatus(Worker worker) {
            String status = script.remove();
            Status s;
            if(status.matches("recommended\\s+for\\s+promotion") || status.matches("recommended_for_promotion")){
                s = Status.RECOMMENDED_FOR_PROMOTION;
            }
            else {
                switch (status.toLowerCase()) {
                    case "fired":
                        s = Status.FIRED;
                        break;
                    case "probation":
                        s = Status.PROBATION;
                        break;
                    default:
                        throw new InvalidStatusException();
                }
            }
            worker.setStatus(s);
    }

    private void curSetPosition(Worker worker) {
            Position p;
            String position = script.remove();
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
                        throw new InvalidPositionException();
                }
            }
            worker.setPosition(p);
    }

    private void curSetEndDate(Worker worker) {
            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String newEndDate = script.remove();
            if(newEndDate.length() == 0) {
                worker.setEndDate(null);
            } else {
                ZonedDateTime endDate = ZonedDateTime.parse(newEndDate);
                worker.setEndDate(endDate);
            }
    }

    private void curSetOrganization(Worker worker) {
            Organization organization = new OrdinaryOrganization();
            String fullName = script.remove();
            if(!fullName.isEmpty()) {
                organization.setFullName(fullName);
                Integer annualTurnover = new Integer(script.remove());
                organization.setAnnualTurnover(annualTurnover);
                curSetOfficialAddress(organization);
                worker.setOrganization(organization);
            }
    }

    private void curSetOfficialAddress(Organization organization) {
            Address address = new OrdinaryAddress();
            address.setStreet(script.remove());
            address.setZipCode(script.remove());
            curSetTown(address);
            organization.setOfficialAddress(address);
    }

    private void curSetTown(Address address) {
        Location town = new OrdinaryLocation();
        town.setName(script.remove());
        Double x = new Double(script.remove());
        town.setX(x);
        Double y = new Double(script.remove());
        town.setY(y);
        address.setTown(town);
    }
}