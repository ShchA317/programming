package data;

import user.Auth;
import exceptions.DBException;
import exceptions.InvalidWorkerFieldException;
import worker.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.util.*;

public class DBReader implements DataReader {
    private final Connection connection;

    public DBReader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<Worker> readElements() throws DBException {
        Collection<Worker> workers = new LinkedList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from  workers");
            while(rs.next()){
                workers.add(createWorker(rs));
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return workers;
    }

    @Override
    public Set<Auth> readUsers() throws DBException {
        Set<Auth> users = new HashSet<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from users");
            while(rs.next()){
                users.add(new Auth(rs.getString("login"), rs.getString( "password")));
            }
            return users;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void getElement(long id) throws DBException {
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery("select * from workers where id = " + id);
            if (rs.next()) {
                createWorker(rs);
            } else {
                throw new InvalidWorkerFieldException("id");
            }

        } catch (SQLException  e){
            throw new DBException(e);
        }
    }

    @Override
    public Worker getLastElement() throws DBException {
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery("select * from workers where id = (select max(id) from workers)");
            if (rs.next()) {
                return createWorker(rs);
            } else {
                return null;
            }
        } catch (SQLException  e){
            throw new DBException(e);
        }
    }

    private Worker  createWorker(ResultSet rs) throws SQLException, DBException {
        Worker worker = new OrdinaryWorker();
        worker.setName(rs.getString("name"));
        Coordinates coordinates = new
                OrdinaryCoordinates(rs.getDouble("coordinate_x"), rs.getFloat("coordinate_y"));
        worker.setCoordinates(coordinates);
        worker.setSalary(rs.getDouble("salary"));
        worker.setPosition(Position.valueOf(rs.getString("position")));
        worker.setStatus(Status.valueOf(rs.getString("status")));
        worker.setCreationDate(rs.getDate("creation_date").toLocalDate().atStartOfDay());

        if(rs.getDate("end_date") != null) {
            worker.setEndDate(ZonedDateTime.from(rs.getDate("end_date").toLocalDate()));
        } else {
            worker.setEndDate(null);
        }

        worker.setId(rs.getLong("id"));

        if(rs.getString("organization_name") != null) {
            Organization organization = new OrdinaryOrganization();
            String full_name = rs.getString("organization_name");

            Set<Organization> organizations = getOrganizations();
            for (Organization o: organizations) {
                if (Objects.equals(o.getFullName(), full_name)){
                    organization = o;
                }
            }
            worker.setOrganization(organization);

        } else {
            worker.setOrganization(null);
        }
        return  worker;
    }

    public Set<Organization> getOrganizations() throws DBException {
        Set<Organization> organizations = new HashSet<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from organizations");
            while(rs.next()){
                organizations.add(createOrganization(rs));
            }
            return organizations;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private Organization createOrganization(ResultSet rs) throws SQLException {
        Organization result = new OrdinaryOrganization();
        result.setFullName(rs.getString("full_name"));
        result.setAnnualTurnover(rs.getInt("annual_turnover"));
        return result;
    }
}
