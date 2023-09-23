package views.CRUDView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import CRUD.PortCRUD;
import CRUD.VehicleCRUD;
import models.port.Port;
import models.vehicle.Vehicle;


public class PortCRUDView {
    public void displayPortCRUD() {
        PortCRUD crud = new PortCRUD();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Display all ports");
            System.out.println("2. List all ships in a Port");
            System.out.println("3. Add a port");
            System.out.println("4. Update a port");
            System.out.println("5. Delete a port");
            System.out.println("6. Exit");
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
                        String portId = reader.readLine();
                        VehicleCRUD vehicleCRUD = new VehicleCRUD();
                        List<Vehicle> vehicleList = vehicleCRUD.readAllVehicles();
                        for (Vehicle vehicle : vehicleList) {
                            if (vehicle.getId().startsWith("sh-") && vehicle.getCurrentPortId().equals(portId)) {
                                System.out.println("Ship Id: " + vehicle.getId());
                                System.out.println("Ship Name: " + vehicle.getName());
                                System.out.println("Ship Fuel Capacity: " + vehicle.getFuelCapacity());
                                System.out.println("Ship Carrying Capacity: " + vehicle.getCarryingCapacity());
                                System.out.println("Ship Current Fuel: " + vehicle.getCurrentFuel());
                                System.out.println("Current Port: " + vehicle.getCurrentPortId());
                                System.out.println();
                            }
                        }
                        break;
                    case 3:
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
                    case 4:
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
                    case 5:
                        System.out.println("Enter Port ID to delete:");
                        String deleteId = reader.readLine();
                        crud.deletePort(deleteId);
                        break;
                    case 6:
                        System.out.println("Going back...");
                        System.out.print("\033c");
                        views.menu.AdminMenu.displayAdminMenu(); 
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
