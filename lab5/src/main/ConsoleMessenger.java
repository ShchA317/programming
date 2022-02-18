package main;

import main.worker.Worker;

/**
 * класс выводящий сообщения в консоль
 */
public class ConsoleMessenger implements Messenger {
    @Override
    public void output(String string) {
        System.out.println(string);
    }

    @Override
    public void output(Worker worker) {
        System.out.println("Type: worker" + "\n" +
                "id: " + worker.getId() + "\n" +
                "name: " + worker.getName() + "\n"+
                "coordinates: X " + worker.getCoordinates().getX() + " Y:" + worker.getCoordinates().getY() + "\n" +
                "creation date: " + worker.getCreationDate() + "\n" +
                "salary: " + worker.getSalary() + "\n" +
                "end date: " + worker.getEndDate() + "\n" +
                "position: " + worker.getPosition() + "\n" +
                "status: " + worker.getStatus() + "\n");
        if (worker.getOrganization() != null) {
            System.out.println("organization: " + worker.getOrganization().getFullName());
            if (worker.getOrganization().getAnnualTurnover() != null) {
                System.out.println("annual turnover: " + worker.getOrganization().getAnnualTurnover());
            } else {
                System.out.println("information about organization is employ");
            }
            if (worker.getOrganization().getOfficialAddress() != null) {
                System.out.println("Official address:");
                if (worker.getOrganization().getOfficialAddress().getStreet() != null) {
                    System.out.println("street: " + worker.getOrganization().getOfficialAddress().getStreet());
                }
                System.out.println("zip code: " + worker.getOrganization().getOfficialAddress().getZipCode());
                System.out.println("town: " + worker.getOrganization().getOfficialAddress().getTown());
            } else {
                System.out.println("workers hasn't got organization");
            }
        }
    }
}