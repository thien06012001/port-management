package views.testing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import CRUD.ContainerCRUD;
import CRUD.PortCRUD;
import CRUD.VehicleCRUD;
import models.container.Container;
import models.port.Port;
import models.vehicle.Vehicle;
import views.menu.Login;

public class ContainerCRUDView {
    

    public static double calculateWeight(String type) {
         double weight = 0;
        ContainerCRUD crud = new ContainerCRUD();
        List<Container> containers = crud.readAllContainers();
        for (Container container : containers) {
            if (container.getType().equals(type)) {
                weight += container.getWeight();
            }
        }
        return weight;
    }

    public static void main(String[] args) {
        ContainerCRUD crud = new ContainerCRUD();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        VehicleCRUD vehicleCRUD = new VehicleCRUD();
        PortCRUD portCRUD = new PortCRUD();

        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Display all containers");
            System.out.println("2. Calculate containers weight");
            System.out.println("3. Add a container");
            System.out.println("4. Update a container");
            System.out.println("5. Delete a container");
            System.out.println("6. Exit");
            try {
                int choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        List<Container> containers = crud.readAllContainers();
                        for (Container container : containers) {
                            System.out.println("Container ID: " + container.getId());
                            System.out.println("Weight: " + container.getWeight());
                            System.out.println("Type: " + container.getType());
                            System.out.println("Status: " + container.getStatus());
                            System.out.println("Location: " + container.getLocationId());
                            System.out.println();
                        }
                        break;
                    case 2:
                        double totalWeight = 0;
                        List<Container> allContainers = crud.readAllContainers();
                        for (Container container : allContainers) {
                            totalWeight += container.getWeight();
                        }
                        System.out.println("All container weight: " + totalWeight);
                        System.out.println("Dry storage weigh: " + calculateWeight("DryStorage"));
                        System.out.println("Liquid weight: " + calculateWeight("Liquid"));
                        System.out.println("Open Side weight: " + calculateWeight("OpenSide"));
                        System.out.println("Open Top weight: " + calculateWeight("OpenTop"));
                        System.out.println("Refrigerated weight: " + calculateWeight("Refrigerated"));
                        System.out.println();
                        break;
                    case 3:
                        System.out.println("Enter Container ID:");
                        String id = reader.readLine();
                        System.out.println("Enter Container Weight:");
                        double weight = Double.parseDouble(reader.readLine());
                        System.out.println("Enter Container Type:");
                        String type = reader.readLine();
                        System.out.println("Enter Status:");
                        String status = reader.readLine();
                        System.out.println("Enter Location Id:");
                        String locationId = reader.readLine();
                        Vehicle vehicle = vehicleCRUD.readVehicle(locationId);
                        Port port = portCRUD.readPort(locationId);
                        if (vehicle == null && port == null) {
                            System.out.println("There is no vehicle/port exist in the system.");
                            break;
                        }
                        crud.addContainer(new Container(id, weight, type, status, locationId));
                        break;
                    case 4:
                        System.out.println("Enter Container ID to update:");
                        String updateId = reader.readLine();
                        System.out.println("Enter new Container Weight:");
                        double updateWeight = Double.parseDouble(reader.readLine());
                        System.out.println("Enter new Container Type:");
                        String updateType = reader.readLine();
                        System.out.println("Enter new Container Status:");
                        String updateStatus = reader.readLine();
                        System.out.println("Enter new Container Location Id:");
                        String updateLocation = reader.readLine();
                        crud.updateContainer(updateId,
                                new Container(updateId, updateWeight, updateType, updateStatus, updateLocation));
                        break;
                    case 5:
                        System.out.println("Enter Container ID to delete:");
                        String deleteId = reader.readLine();
                        crud.deleteContainer(deleteId);
                        break;
                    case 6:
                        System.out.println("Going back...");
                        System.out.print("\033c");
                        views.menu.Login.displayLogin(); // Call the displayLogin method from the Login class
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
