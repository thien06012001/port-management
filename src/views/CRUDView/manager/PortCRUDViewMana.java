package views.CRUDView.manager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import CRUD.PortCRUD;
import CRUD.UserCRUD;
import models.port.Port;
import models.user.User;

public class PortCRUDViewMana {
    static PortCRUD crud = new PortCRUD();
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void displayAllPorts() {
        System.out.println("\033c");
        List<Port> ports = crud.readAllPorts();

        for (Port port : ports) {
            System.out.println("Port ID: " + port.getId());
            System.out.println("Name: " + port.getName());
            System.out.println("Latitude: " + port.getLatitude());
            System.out.println("Longitude: " + port.getLongitude());
            System.out.println("Storing capacity: " + port.getStoringCapacity());
            System.out.println("Landing ability: " + port.getLandingAbility());
            System.out.println();

        }
    }

    public static void updatedAssociatedPort() {
        try {
            UserCRUD userCRUD = new UserCRUD();
            User currentUser = userCRUD.readAuthenticatedUser();
            if (currentUser == null) {
                System.out.println("\033c");
                System.out.println("User is unauthenticated");
            }
            System.out.println("Enter Port Name:");
            String updatedName = reader.readLine();
            System.out.println("Enter Port Latitude:");
            double updatedLatitude = Double.parseDouble(reader.readLine());
            System.out.println("Enter Port Longitude:");
            double updatedLongitude = Double.parseDouble(reader.readLine());
            System.out.println("Enter Port Storing Capacity:");
            double updatedStoringCapacity = Double.parseDouble(reader.readLine());
            System.out.println("Enter Port Landing Ability:");
            Boolean updatedLandingAbility = Boolean.parseBoolean(reader.readLine());
            crud.updatePort(currentUser.getAssociatedPort(),
                    new Port(currentUser.getAssociatedPort(), updatedName, updatedLatitude,
                            updatedLongitude,
                            updatedStoringCapacity, updatedLandingAbility));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void displayPortCRUD() {
        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Display all ports");
            System.out.println("2. Update associated port");
            System.out.println("3. Exit");
            try {
                int choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        displayAllPorts();
                        break;
                    case 2:
                        updatedAssociatedPort();
                        break;
                    case 3:
                        System.out.println("Going back...");
                        System.out.print("\033c");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
