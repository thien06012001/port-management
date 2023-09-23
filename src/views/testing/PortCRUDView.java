package views.testing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import CRUD.PortCRUD;
import models.port.Port;
import views.menu.Login;

public class PortCRUDView {
     public static void main(String[] args) {
        PortCRUD crud = new PortCRUD();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Login login = new Login();
        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Display all ports");
            System.out.println("2. Add a port");
            System.out.println("3. Update a port");
            System.out.println("4. Delete a port");
            System.out.println("5. Exit");
            try {
                int choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        List<Port> ports = crud.readAllPorts();
                        for (Port port : ports) {
                            System.out.println("Container ID: " + port.getId());
                            System.out.println("Name: " + port.getName());
                            System.out.println("Latitude: " + port.getLatitude());
                            System.out.println("Longitude: " + port.getLongitude());
                            System.out.println("Storing capacity: " + port.getStoringCapacity());
                            System.out.println("Landing ability: " + port.getLandingAbility());
                            System.out.println();
                        }
                        break;
                    case 2:
                        System.out.println("Enter Port ID:");
                        String id = reader.readLine();
                        System.out.println("Enter Port Name:");
                        String name = reader.readLine();
                        System.out.println("Enter Port Latitude:");
                        double latitude = Double.parseDouble(reader.readLine());
                        System.out.println("Enter Port Longitude:");
                        double longitude = Double.parseDouble(reader.readLine());
                        System.out.println("Enter Port Storing Capacity:");
                        double storingCapacity = Double.parseDouble(reader.readLine());
                        System.out.println("Enter Port Landing Ability:");
                        Boolean landingAbility = Boolean.parseBoolean(reader.readLine());
                        crud.addPort(new Port(id, name, latitude, longitude, storingCapacity, landingAbility));
                        break;
                    case 3:
                        System.out.println("Enter Port ID to update:");
                        String updateId = reader.readLine();
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
                        crud.updatePort(updateId, new Port(updateId, updatedName, updatedLatitude, updatedLongitude,
                                updatedStoringCapacity, updatedLandingAbility));
                        break;
                    case 4:
                        System.out.println("Enter Port ID to delete:");
                        String deleteId = reader.readLine();
                        crud.deletePort(deleteId);
                        break;
                    case 5:
                        System.out.println("Going back...");
                        System.out.print("\033c");
                        login.displayLogin(); // Call the displayLogin method from the Login class
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
