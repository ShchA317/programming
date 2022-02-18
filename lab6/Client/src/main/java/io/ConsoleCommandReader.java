package io;

import command.Command;
import command.CommandWithOrganizationArg;
import command.CommandWithWorkerArg;
import command.SimpleCommandWithArg;
import exceptions.InvalidWorkerFieldException;
import exceptions.UnknownCommandException;
import worker.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;

public class ConsoleCommandReader implements CommandReader {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private ClientWriter clientWriter;
    private Map<String, Command> commands;
    ResourceBundle bundle;

    public ConsoleCommandReader(ClientWriter clientWriter, Map<String, Command> commands, ResourceBundle bundle) {
        this.clientWriter = clientWriter;
        this.commands = commands;
        this.bundle = bundle;
    }

    @Override
    public Command readCommand() throws IOException {
        String[] input;
        String inputString;
        Command command;
        inputString = reader.readLine();
        if (inputString == null) {
            System.err.println("Ну зачем Ctrl-D? :(");
            throw new NullPointerException();
        }
        input = inputString.trim().toLowerCase().split("\\s+");
        command = commands.get(input[0]);
        if (command == null) {
            throw new UnknownCommandException();
        }
        if(command instanceof SimpleCommandWithArg){
            try {
                if (input[1] != null) {
                    ((SimpleCommandWithArg) command).setSimpleArg(input[1]);
                }
            } catch (IndexOutOfBoundsException iobe){
                throw new UnknownCommandException();
            }
        }
        if(command instanceof CommandWithWorkerArg){
            ((CommandWithWorkerArg) command).setArg((OrdinaryWorker) readWorker());
        }
        if(command instanceof CommandWithOrganizationArg){
            ((CommandWithOrganizationArg) command).setArg(readOrganization());
        }
        return command;
    }

    public Worker readWorker(){
        Worker worker = new OrdinaryWorker();
        clientWriter.write(bundle.getString("input_worker_field_message"));
        curSetName(worker);
        clientWriter.write(bundle.getString("input_coordinates_message"));
        curSetCoordinates(worker);
        clientWriter.write(bundle.getString("input_salary_message"));
        curSetSalary(worker);
        curSetEndDate(worker);
        curSetPosition(worker);
        curSetStatus(worker);
        curSetOrganization(worker);
        clientWriter.write(bundle.getString("end_of_input_worker_message"));
        return worker;
    }

    public Organization readOrganization() {
        Worker worker = new OrdinaryWorker();
        curSetOrganization(worker);
        return worker.getOrganization();
    }

    private void curSetName(Worker worker) {
        try {
            clientWriter.write(bundle.getString("input_name_message"));
            String name = reader.readLine().trim().toLowerCase();
            worker.setName(name);
        }catch (InvalidWorkerFieldException | IOException e){
            if( e.getMessage() != null){
                clientWriter.write(e.getMessage());
            }
            clientWriter.write(bundle.getString("try_again_message"));
            curSetName(worker);
        }
    }

    private void curSetCoordinates(Worker worker) {
        try{
            Coordinates coordinates = new OrdinaryCoordinates();
            clientWriter.write(bundle.getString("input_x_coordinate_message"));
            String curX = reader.readLine().trim();
            if(curX.isEmpty()){
                throw new InvalidWorkerFieldException(bundle.getString("invalid_coordinates"));
            }
            Double x = new Double(curX);
            coordinates.setX(x);
            clientWriter.write(bundle.getString("input_y_coordinate_message"));

            String curY = reader.readLine().trim();
            if(curY.isEmpty()){
                throw new InvalidWorkerFieldException(bundle.getString("invalid_coordinates"));
            }
            float y = new Float(curY);
            coordinates.setY(y);
            worker.setCoordinates(coordinates);
        } catch (Exception e){
            if (e.getMessage() != null){
                clientWriter.write(e.getMessage());
            }
            clientWriter.write(bundle.getString("try_again_message"));
            curSetCoordinates(worker);
        }

    }

    private void curSetSalary(Worker worker) {
        try {
            String curSalary = reader.readLine().trim();
            if(curSalary.isEmpty()){
                throw new InvalidWorkerFieldException(bundle.getString("invalid_salary_message"));
            }
            Double salary = new Double(curSalary);
            worker.setSalary(salary);
        } catch (Exception e){
            clientWriter.write(bundle.getString("invalid_salary_message"));
            clientWriter.write(bundle.getString("try_again_message"));
            curSetSalary(worker);
        }
    }

    private void curSetStatus(Worker worker) {
        try{
            clientWriter.write(bundle.getString("input_status_message"));
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
                        throw new InvalidWorkerFieldException(bundle.getString("invalid_status"));
                }
            }
            worker.setStatus(s);
        } catch (InvalidWorkerFieldException | IOException e) {
            if(e.getMessage() != null)
                clientWriter.write(e.getMessage());
            clientWriter.write(bundle.getString("try_again_message"));
            curSetStatus(worker);
        }
    }

    private void curSetPosition(Worker worker) {
        try {
            clientWriter.write(bundle.getString("input_position_message"));
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
                        throw new InvalidWorkerFieldException(bundle.getString("invalid_position"));
                }
            }
            worker.setPosition(p);
        } catch (InvalidWorkerFieldException|IOException e){
            if(e.getMessage() != null)
                clientWriter.write(e.getMessage());
            clientWriter.write(bundle.getString("try_again_message"));
            curSetPosition(worker);
        }
    }

    private void curSetEndDate(Worker worker) {
        try {
            clientWriter.write(bundle.getString("input_end_date_message"));
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String newEndDate = reader.readLine().trim();
            if(newEndDate.length() == 0) {
                worker.setEndDate(null);
            } else {
                Date endDate1 = df.parse(newEndDate);
                ZonedDateTime endDate = ZonedDateTime.ofInstant(endDate1.toInstant(), ZoneId.systemDefault());
                worker.setEndDate(endDate);
            }
        } catch (InvalidWorkerFieldException | IOException | DateTimeParseException | ParseException e){
            if(e.getMessage() != null){
                clientWriter.write(e.getMessage());
            }
            clientWriter.write(bundle.getString("try_again_message"));
            curSetEndDate(worker);
        }
    }

    private void curSetOrganization(Worker worker) {
        try {
            clientWriter.write(bundle.getString("input_organization_field_message"));
            Organization organization = new OrdinaryOrganization();
            clientWriter.write(bundle.getString("input_full_organization_name"));
            String fullName = reader.readLine().trim();
            if(!fullName.isEmpty()){
                organization.setFullName(fullName);
                clientWriter.write(bundle.getString("input_annual_turnover_message"));
                Integer annualTurnover = Integer.valueOf(reader.readLine().trim());
                organization.setAnnualTurnover(annualTurnover);
                clientWriter.write(bundle.getString("input_official_address_message"));
                curSetOfficialAddress(organization);
                worker.setOrganization(organization);
                clientWriter.write(bundle.getString("end_of_input_organization"));
            }
        } catch (Exception e){
            if(e.getMessage() != null){
                clientWriter.write(e.getMessage());
            }
            clientWriter.write(bundle.getString("try_again_message"));
            curSetOrganization(worker);
        }
    }

    private void curSetOfficialAddress(Organization organization) {
        try{
            Address address = new OrdinaryAddress();
            clientWriter.write(bundle.getString("input_street_message"));
            address.setStreet(reader.readLine().trim());
            clientWriter.write(bundle.getString("input_zipcode_message"));
            address.setZipCode(reader.readLine().trim());
            curSetTown(address);
            organization.setOfficialAddress(address);
        } catch(Exception e) {
            if(e.getMessage() != null){
                clientWriter.write(e.getMessage());
            }
            clientWriter.write(bundle.getString("try_again_message"));
            curSetOfficialAddress(organization);
        }
    }

    private void curSetTown(Address address) {
        try {
            clientWriter.write(bundle.getString("input_town_name_message"));
            Location town = new OrdinaryLocation();
            town.setName(reader.readLine().trim());

            clientWriter.write(bundle.getString("input_x_coordinate_message"));
            Double x = new Double(reader.readLine().trim());
            town.setX(x);

            clientWriter.write(bundle.getString("input_y_coordinate_message"));
            Double y = new Double(reader.readLine().trim());
            town.setY(y);

            address.setTown(town);
        } catch(InvalidWorkerFieldException |IOException|NumberFormatException e) {
            if(e.getMessage() != null){
                clientWriter.write(e.getMessage());
            }
            clientWriter.write(bundle.getString("try_again_message"));
            curSetTown(address);
        }
    }
}