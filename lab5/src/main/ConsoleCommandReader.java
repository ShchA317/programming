package main;

import exeptions.*;
import main.worker.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * класс для чтения команд и данных с консоли
 */
public class ConsoleCommandReader extends AbstractCommandReader{
    Messenger messenger;
    public ConsoleCommandReader(Messenger messenger, CommandFactory commandFactory, CollectionOfWorkerManager collectionOfWorkerManager, Messages messages) {
        super(collectionOfWorkerManager, messages, messenger, commandFactory);
        this.messenger = messenger;
        this.commandFactory = commandFactory;
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * метод для чтения команд
     */
    @Override
    public void readCommands() {
        isRunning = true;
        String input;
        while(isRunning){
            try {
                input = reader.readLine();
                if(input == null){
                    throw new RuntimeException("НУ ЗАЧЕМ Ctrl-D?");
                }
                String[] command = input.trim().split("\\s++");
                command[0] = command[0].toLowerCase();
                decodeAndTryExecuteCommand(command);
                }
            catch (IOException e){
                System.err.println("ошибка с консолью");
            }
        }
    }

    @Override
    public void exit() {
        isRunning = false;
    }

    @Override
    public Worker readWorker(){
        Worker worker = new OrdinaryWorker();
        messenger.output(messages.getInputFieldMessage());
        curSetName(worker);
        messenger.output(messages.getInputCoordinatesMassage());
        curSetCoordinates(worker);
        messenger.output(messages.getInputSalaryMessage());
        curSetSalary(worker);
        curSetEndDate(worker);
        curSetPosition(worker);
        curSetStatus(worker);
        messenger.output(messages.getInputOrganizationMessage());
        curSetOrganization(worker);
        messenger.output(messages.getWasAddedMassage());
        return worker;
    }

    @Override
    public Organization readOrganization() {
        Worker worker = new OrdinaryWorker();
        curSetOrganization(worker);
        return worker.getOrganization();
    }

    private void curSetName(Worker worker) {
        try {
            messenger.output(messages.getInputNameMassage());
            String name = reader.readLine().trim().toLowerCase();
            worker.setName(name);
        }catch (InvalidNameException | IOException e){
            if( e.getMessage() != null){
                messenger.output(e.getMessage());
            }
            messenger.output(messages.getTryAgainMessage());
            messenger.output(messages.getInputNameMassage());
            curSetName(worker);
        }
    }

    private void curSetCoordinates(Worker worker) {
        try{
            Coordinates coordinates = new OrdinaryCoordinates();
            messenger.output(messages.getInputXCoordinatesMessage());
            String curX = reader.readLine().trim();
            if(curX.isEmpty()){
                throw new InvalidCoordinatesException();
            }
            Double x = new Double(curX);
            coordinates.setX(x);
            messenger.output(messages.getInputYCoordinatesMessage());

            String curY = reader.readLine().trim();
            if(curY.isEmpty()){
                throw new InvalidCoordinatesException();
            }
            float y = new Float(curY);
            coordinates.setY(y);
            worker.setCoordinates(coordinates);
        } catch (Exception e){
            if (e.getMessage() != null){
                messenger.output(e.getMessage());
            }
            messenger.output(messages.getTryAgainMessage());
            curSetCoordinates(worker);
        }

    }

    private void curSetSalary(Worker worker) {
        try {
            messenger.output(messages.getInputSalaryMessage());
            String curSalary = reader.readLine().trim();
            if(curSalary.isEmpty()){
                throw new InvalidWorkerFieldException();
            }
            Double salary = new Double(curSalary);
            worker.setSalary(salary);
        } catch (Exception e){
            if(e.getMessage() != null){
                messenger.output(e.getMessage());
            }
            messenger.output(messages.getTryAgainMessage());
            curSetSalary(worker);
        }
    }

    private void curSetStatus(Worker worker) {
        try{
            messenger.output(messages.getInputStatusMessage());
            String status = reader.readLine().trim().toLowerCase();
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
        } catch (InvalidWorkerFieldException | IOException e) {
            if(e.getMessage() != null)
                messenger.output(e.getMessage());
            messenger.output(messages.getTryAgainMessage());
            curSetStatus(worker);
        }
    }

    private void curSetPosition(Worker worker) {
        try {
            messenger.output(messages.getInputPositionMessage());
            Position p;
            String position = reader.readLine().toLowerCase().trim();
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
        } catch (InvalidWorkerFieldException|IOException e){
            if(e.getMessage() != null)
                messenger.output(e.getMessage());
            messenger.output(messages.getTryAgainMessage());
            curSetPosition(worker);
        }
    }

    private void curSetEndDate(Worker worker) {
        try {
            messenger.output(messages.getInputEndDateMessage());
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String newEndDate = reader.readLine().trim();
            if(newEndDate.length() == 0) {
                worker.setEndDate(null);
            }
            else {
                Date endDate1 = df.parse(newEndDate);
                ZonedDateTime endDate = ZonedDateTime.ofInstant(endDate1.toInstant(), ZoneId.systemDefault());
                worker.setEndDate(endDate);
            }
        } catch (InvalidWorkerFieldException | IOException | DateTimeParseException | ParseException e){
            if(e.getMessage() != null){
                messenger.output(e.getMessage());
            }
            messenger.output(messages.getTryAgainMessage());
            curSetEndDate(worker);
        }
    }

    private void curSetOrganization(Worker worker) {
        try {
            messenger.output(messages.getInputFullNameOrganizationMessage());
            Organization organization = new OrdinaryOrganization();
            String fullName = reader.readLine().trim();
            if(fullName.isEmpty()){
                organization = null;
            }
            else {
                organization.setFullName(fullName);
                messenger.output(messages.getInputAnnualTurnoverOrganizationMessage());
                Integer annualTurnover = new Integer(reader.readLine().trim());
                organization.setAnnualTurnover(annualTurnover);
                messenger.output(messages.getInputOfficialAddressMessage());
                curSetOfficialAddress(organization);
                worker.setOrganization(organization);
            }
        } catch (Exception e){
            if(e.getMessage() != null){
                messenger.output(e.getMessage());
            }
            messenger.output(messages.getTryAgainMessage());
            curSetOrganization(worker);
        }
    }

    private void curSetOfficialAddress(Organization organization) {
        try{
            Address address = new OrdinaryAddress();
            messenger.output(messages.getInputStreetForAddress());
            address.setStreet(reader.readLine().trim());
            address.setZipCode(reader.readLine().trim());
            curSetTown(address);
            organization.setOfficialAddress(address);
        } catch(Exception e) {
            if(e.getMessage() != null){
                messenger.output(e.getMessage());
            }
            messenger.output(messages.getTryAgainMessage());
            curSetOfficialAddress(organization);
        }
    }

    private void curSetTown(Address address) {
        try {
            messenger.output(messages.getInputTownName());
            Location town = new OrdinaryLocation();
            town.setName(reader.readLine().trim());

            messenger.output(messages.getInputTownX());
            Double x = new Double(reader.readLine().trim());
            town.setX(x);

            messenger.output(messages.getInputTownY());
            Double y = new Double(reader.readLine().trim());
            town.setY(y);

            address.setTown(town);
        } catch(InvalidWorkerFieldException|IOException|NumberFormatException e) {
            if(e.getMessage() != null){
                messenger.output(e.getMessage());
            }
            messenger.output(messages.getTryAgainMessage());
            curSetTown(address);
        }
    }
}
