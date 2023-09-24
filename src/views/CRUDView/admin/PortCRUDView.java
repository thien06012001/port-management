package views.CRUDView.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import CRUD.PortCRUD;
import CRUD.VehicleCRUD;
import models.port.Port;
import models.vehicle.Vehicle;

public class PortCRUDView {
    static PortCRUD crud = new PortCRUD();
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void displayAllPorts() {
        System.out.println("\033c");
        System.out.println("Here are all the available ports: ");
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

    public static void ListAllShipsInPort() throws IOException {
        System.out.println("\033c");
        System.out.println("Enter Port ID:");
        String portId;
        portId = reader.readLine();
        Port port = crud.readPort(portId);
        if(port == null) {
            System.out.println("Port does not exist in the system");
            return;
        }
      

        VehicleCRUD vehicleCRUD = new VehicleCRUD();
        List<Vehicle> vehicleList = vehicleCRUD.readAllVehicles();
        System.out.println("Here are all the ships in port " + portId + ": ");
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
    }

    public static void addAPort() throws IOException {
        System.out.println("\033c");
        System.out.println("Enter Port ID:");
        String id = reader.readLine();
        Port port = crud.readPort(id);
        if(port != null) {
            System.out.println("Port already exist in the system");
            return;
        }
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
        System.out.println("\033c");
        System.out.println("Port added successfully!");
       
    }

    public static void updateAPort() throws IOException {
        System.out.println("\033c");
        System.out.println("Enter Port ID to update:");
        String updateId = reader.readLine();
        Port port = crud.readPort(updateId);
        if(port == null) {
            System.out.println("Port does not exist in the system");
            return;
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
        crud.updatePort(updateId, new Port(updateId, updatedName, updatedLatitude, updatedLongitude,
                updatedStoringCapacity, updatedLandingAbility));
        System.out.println("\033c");
        System.out.println("Update Port successfully");
    }

    public static void deleteAPort() throws IOException {
        System.out.println("\033c");
        System.out.println("Enter Port ID to delete:");
        String deleteId = reader.readLine();
         Port port = crud.readPort(deleteId);
        if(port == null) {
            System.out.println("Port does not exist in the system");
            return;
        }
        crud.deletePort(deleteId);
        System.out.println("Delete Port successfully!");
    }

    public static void backToAdminMenu() {
        System.out.println("\033c");
        System.out.println("Going back...");
        System.out.print("\033c");
        views.menu.AdminMenu.displayAdminMenu();
    }

    public void displayPortCRUD() {

        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Display all ports");
            System.out.println("2. List all ships in a Port");
            System.out.println("3. Add a port");
            System.out.println("4. Update a port");
            System.out.println("5. Delete a port");
            System.out.println("6. Back");
            try {
                int choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║          All Port          ║");
                        System.out.println("╚════════════════════════════╝");
                        displayAllPorts();
                        break;
                    case 2:
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║         Ship In Port       ║");
                        System.out.println("╚════════════════════════════╝");
                        ListAllShipsInPort();
                        break;
                    case 3:
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║           Add Port         ║");
                        System.out.println("╚════════════════════════════╝");
                        addAPort();
                        break;
                    case 4:
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║         Update Port        ║");
                        System.out.println("╚════════════════════════════╝");
                        updateAPort();
                        break;
                    case 5:
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║         Delete Port        ║");
                        System.out.println("╚════════════════════════════╝");
                        deleteAPort();
                        break;
                    case 6:
                    System.out.println("Going back...");
                    System.out.print("\033c");
                        return;
                    default:
                        System.out.println("\033c");
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
