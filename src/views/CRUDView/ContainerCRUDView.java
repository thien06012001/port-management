package views.CRUDView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import CRUD.ContainerCRUD;
import CRUD.PortCRUD;
import CRUD.VehicleCRUD;
import models.container.Container;
import models.port.Port;
import models.vehicle.Vehicle;

public class ContainerCRUDView {
    static ContainerCRUD crud = new ContainerCRUD();
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static VehicleCRUD vehicleCRUD = new VehicleCRUD();
    static PortCRUD portCRUD = new PortCRUD();

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

    public static void displayAllContainers() {
        System.out.println("\033c");
        List<Container> containers = crud.readAllContainers();
        for (Container container : containers) {
            System.out.println("Container ID: " + container.getId());
            System.out.println("Weight: " + container.getWeight());
            System.out.println("Type: " + container.getType());
            System.out.println("Status: " + container.getStatus());
            System.out.println("Location: " + container.getLocationId());
            System.out.println();
        }
    }

    public static void calculateContainersWeight() {
        System.out.println("\033c");
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
    }

    public static void addAContainer() {
        System.out.println("\033c");
        try {
            System.out.println("Enter Container ID:");
            String id = reader.readLine();
            Container container = crud.readContainerById(id);
            if (container != null) {
                System.out.println("\033c");
                System.out.println("The container already exist.");
                return;
            }
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
                System.out.println("\033c");
                System.out.println("There is no vehicle/port exist in the system.");
                return;
            }
            double currentWeight = 0;
            List<Container> containersInPort = crud.readAllContainers();
            for (Container containerInPort : containersInPort) {
                if (containerInPort.getLocationId().equals(locationId)) {
                    currentWeight += containerInPort.getWeight();
                }
            }
            if (weight + currentWeight > port.getStoringCapacity()) {
                System.out.println("\033c");
                System.out.println("Exceeding the maximum capacity.");
                return;
            }
            crud.addContainer(new Container(id, weight, type, status, locationId));
            System.out.println("\033c");
            System.out.println("Added container successfully!");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void updateAContainer() {
        System.out.println("\033c");
        try {
            System.out.println("Enter Container ID to update:");
            String updateId = reader.readLine();
            Container container = crud.readContainerById(updateId);
            if (container == null) {
                System.out.println("\033c");
                System.out.println("The container does not exist.");
                return;
            }
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
            System.out.println("\033c");
            System.out.println("Update container successfully!");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void deleteAContainer() {
        System.out.println("\033c");
        try {
            System.out.println("Enter Container ID to delete:");
            String deleteId = reader.readLine();
            Container container = crud.readContainerById(deleteId);
            if (container == null) {
                System.out.println("\033c");
                System.out.println("The container does not exist.");
                return;
            }
            crud.deleteContainer(deleteId);
            System.out.println("\033c");
            System.out.println("Delete container successfully!");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void displayContainerCRUD() {

        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Display all containers");
            System.out.println("2. Calculate containers weight");
            System.out.println("3. Add a container");
            System.out.println("4. Update a container");
            System.out.println("5. Delete a container");
            System.out.println("6. Back to menu");
            try {
                int choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        displayAllContainers();
                        break;
                    case 2:
                        calculateContainersWeight();
                        break;
                    case 3:
                        addAContainer();
                        break;
                    case 4:
                        updateAContainer();
                        break;
                    case 5:
                        deleteAContainer();
                        break;
                    case 6:
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
