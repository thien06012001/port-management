package views.CRUDView.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import CRUD.ContainerCRUD;
import CRUD.PortCRUD;
import CRUD.UserCRUD;
import CRUD.VehicleCRUD;
import models.container.Container;
import models.port.Port;
import models.user.User;
import models.vehicle.Ship;
import models.vehicle.Truck;
import models.vehicle.Vehicle;

public class VehicleCRUDViewMana {
    static VehicleCRUD crud = new VehicleCRUD();
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static PortCRUD portCRUD = new PortCRUD();
    private static UserCRUD userCRUD = new UserCRUD();
    private static User currentUser = userCRUD.readAuthenticatedUser();

    public static void displayAllVehicle() {
        System.out.println("\033c");
        List<Vehicle> vehicles = crud.readAllVehicles();
        for (Vehicle vehicle : vehicles) {
            System.out.println("Vehicle ID: " + vehicle.getId());
            System.out.println("Name: " + vehicle.getName());
            System.out.println("Type: " + vehicle.getType());
            System.out.println("Fuel Capacity: " + vehicle.getFuelCapacity());
            System.out.println("Carrying Capacity: " + vehicle.getCarryingCapacity());
            System.out.println("Current Fuel: " + vehicle.getCurrentFuel());
            System.out.println("Current Port Id: " + vehicle.getCurrentPortId());
            // Count containers for this vehicle
            int containerCount = crud.countContainersForVehicle(vehicle.getId());
            System.out.println("Total Containers: " + containerCount);
            int dryStorageCount = crud.countSpecificContainersForVehicle(vehicle.getId(), "DryStorage");
            int liquidCount = crud.countSpecificContainersForVehicle(vehicle.getId(), "Liquid");
            int openSideCount = crud.countSpecificContainersForVehicle(vehicle.getId(), "OpenSide");
            int openTopCount = crud.countSpecificContainersForVehicle(vehicle.getId(), "OpenTop");
            int refrigeratedCount = crud.countSpecificContainersForVehicle(vehicle.getId(),
                    "Refrigerated");
            System.out.println("DryStorage Containers: " + dryStorageCount);
            System.out.println("Liquid Containers: " + liquidCount);
            System.out.println("OpenSide Containers: " + openSideCount);
            System.out.println("OpenTop Containers: " + openTopCount);
            System.out.println("Refrigerated Containers: " + refrigeratedCount);
            System.out.println();
        }
    }

    public static void addAVehicle() {
        System.out.println("\033c");
        try {
            System.out.println("Enter Vehicle ID:");
            String id = reader.readLine();
            // Check if the entered vehicleId already exists in the Vehicle.txt file
            Vehicle existingVehicle = crud.readVehicle(id);
            if (existingVehicle != null) {
                System.out.println(
                        "Error: A vehicle with the entered ID already exists. Please enter a unique ID.");
                return;

            }
            System.out.println("Enter Vehicle Name:");
            String name = reader.readLine();
            System.out.println("Enter Vehicle Type (Truck/Ship):");
            String type = reader.readLine();
            System.out.println("Enter Fuel Capacity:");
            double fuelCapacity = Double.parseDouble(reader.readLine());
            System.out.println("Enter Carrying Capacity:");
            double carryingCapacity = Double.parseDouble(reader.readLine());
            System.out.println("Enter Current Fuel: ");
            double currentFuel = Double.parseDouble(reader.readLine());
            System.out.println("Enter current port Id: ");
            String currentPortId = reader.readLine();
            Port currentPort = portCRUD.readPort(currentPortId);
            if (currentPort == null) {
                System.out.println(
                        "Error: The entered currentPortID does not match any port in the system.");
            }
            if ("Truck".equalsIgnoreCase(type)) {
                System.out.println("Enter Truck Type (e.g., Basic, Reefer, Tanker):");
                String truckType = reader.readLine();
                crud.addVehicle(new Truck(id, name, fuelCapacity, carryingCapacity, currentFuel,
                        truckType, currentPortId));
            } else if ("Ship".equalsIgnoreCase(type)) {
                crud.addVehicle(
                        new Ship(id, name, fuelCapacity, carryingCapacity, currentFuel, currentPortId));
            } else {
                System.out.println("Invalid vehicle type!");
            }
        } catch (NumberFormatException e) {
            System.out.println(
                    "Please enter a valid number for fuel capacity, carrying capacity, or current fuel.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void updateAVehicle() {
        System.out.println("\033c");
        try {
            System.out.println("\033c");
            System.out.println("Enter Vehicle ID to update:");
            String updateId = reader.readLine();
            Vehicle existingVehicle = crud.readVehicle(updateId);
            if (existingVehicle == null) {
                System.out.println("\033c");
                System.out.println(
                        "Error: No vehicle with the entered ID exists. Please enter a valid ID.");
                return;
            }
            if (!existingVehicle.getCurrentPortId().equals(currentUser.getAssociatedPort())) {
                System.out.println("\033c");
                System.out.println("Vehicle does not exist in this port.");
                return;
            }
            // Check if the entered vehicleId exists in the Vehicle.txt file

            System.out.println("Enter new Vehicle Name:");
            String updateName = reader.readLine();
            System.out.println("Enter new Vehicle Type (Truck/Ship):");
            String updateType = reader.readLine();
            System.out.println("Enter new Fuel Capacity:");
            double updateFuelCapacity = Double.parseDouble(reader.readLine());
            System.out.println("Enter new Carrying Capacity:");
            double updateCarryingCapacity = Double.parseDouble(reader.readLine());
            System.out.println("Enter new Current Fuel:");
            double updateCurrentFuel = Double.parseDouble(reader.readLine());

            if ("Truck".equalsIgnoreCase(updateType)) {
                System.out.println("Enter new Truck Type (e.g., Basic, Reefer, Tanker):");
                String updateTruckType = reader.readLine();
                crud.updateVehicle(updateId,
                        new Truck(updateId, updateName, updateFuelCapacity,
                                updateCarryingCapacity, updateCurrentFuel, updateTruckType,
                                existingVehicle.getCurrentPortId()));
                System.out.println("\033c");
                System.out.println("Update successfully!");
            } else if ("Ship".equalsIgnoreCase(updateType)) {
                crud.updateVehicle(updateId,
                        new Ship(updateId, updateName, updateFuelCapacity,
                                updateCarryingCapacity, updateCurrentFuel, existingVehicle.getCurrentPortId()));
                System.out.println("\033c");
                System.out.println("Update successfully!");
            } else {
                System.out.println("Invalid vehicle type!");
            }
        } catch (NumberFormatException e) {
            System.out.println("\033c");
            System.out.println(
                    "Please enter a valid number for fuel capacity, carrying capacity, or current fuel.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void deleteAVehicle() {
        System.out.println("\033c");
        System.out.println("Enter Vehicle ID to delete:");
        String deleteId;
        try {
            deleteId = reader.readLine();
            crud.deleteVehicle(deleteId);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void loadContainer() {
        System.out.println("\033c");
        boolean canLoad = true;
        try {
            System.out.println("Assign a container to a truck");
            System.out.println("Enter Vehicle ID:");
            String vehicleId = reader.readLine();
            Vehicle currentVehicle = crud.readVehicle(vehicleId);
            if (currentVehicle == null) {
                System.out.println("\033c");
                System.out.println(
                        "Error: No vehicle with the entered ID exists. Please enter a valid ID.");
                return;
            }
            if (!currentVehicle.getCurrentPortId().equals(currentUser.getAssociatedPort())) {
                System.out.println("\033c");
                System.out.println("Vehicle does not exist in this port.");
                return;
            }
            System.out.println("Enter Container ID:");
            String containerId = reader.readLine();
            ContainerCRUD containerCRUD = new ContainerCRUD();
            Container currentContainer = containerCRUD.readContainerById(containerId);
            if (currentContainer == null) {
                System.out.println("\033c");
                System.out.println("The container does not exist.");
                return;
            }
            if (currentContainer.getStatus() == "Loaded") {
                System.out.println("\033c");
                System.out.println("The container is already loaded.");
                return;
            }
            if (!currentContainer.getLocationId().equals(currentVehicle.getCurrentPortId())) {
                System.out.println("\033c");
                System.out.println("The container and the vehicle are not on the same port.");
                return;
            }
            if (vehicleId.startsWith("tr-")) {
                Truck truck = (Truck) currentVehicle;
                String truckType = truck.getTruckType();

                switch (truckType) {
                    case "Basic":
                        if (currentContainer.getType().equals("DryStorage")
                                || currentContainer.getType().equals("OpenTop")
                                || currentContainer.getType().equals("OpenSide")) {
                            canLoad = true;
                        } else {
                            System.out.println("\033c");
                            System.out.println(
                                    "Basic truck can't carry this container type: "
                                            + currentContainer.getType());
                        }
                        break;

                    case "Reefer":
                        if (currentContainer.getType().equals("Refrigerated")) {
                            canLoad = true;
                        } else {
                            System.out.println("\033c");
                            System.out.println(
                                    "Reefer truck can't carry this container type: "
                                            + currentContainer.getType());
                        }
                        break;

                    case "Tanker":
                        if (currentContainer.getType().equals("Liquid")) {
                            canLoad = true;
                        } else {
                            System.out.println("\033c");
                            System.out.println(
                                    "Tanker truck can't carry this container type: "
                                            + currentContainer.getType());
                        }
                        break;

                    default:
                        System.out.println("\033c");
                        System.out.println("Unknown truck type: " + truckType);
                        break;
                }

            }
            if (canLoad) {
                // getContainersList().add(container);
                double totalWeightInVehicle = 0.0;
                List<Container> containersList = containerCRUD.readAllContainers();
                for (Container container : containersList) {
                    if (container.getLocationId().equals(currentVehicle.getId())) {
                        totalWeightInVehicle += container.getWeight();
                    }
                }
                if (currentContainer.getWeight() + totalWeightInVehicle <= currentVehicle
                        .getCarryingCapacity()) {

                    containerCRUD.updateContainer(containerId,
                            new Container(containerId, currentContainer.getWeight(),
                                    currentContainer.getType(), "Loaded", vehicleId));
                    System.out.println("\033c");
                    System.out.println("The container is loaded!");
                } else {
                    System.out.println("\033c");
                    System.out.println("Too much weight to load this container!");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void unloadContainer() {
        System.out.println("\033c");
        try {
            System.out.println("Unload container from vehicle");
            System.out.println("Enter Vehicle ID:");
            String unloadVehicleId = reader.readLine();
            Vehicle currentUnloadVehicle = crud.readVehicle(unloadVehicleId);
            if (currentUnloadVehicle == null) {
                System.out.println("\033c");
                System.out.println(
                        "Error: No vehicle with the entered ID exists. Please enter a valid ID.");
                return;
            }
            if (!currentUnloadVehicle.getCurrentPortId().equals(currentUser.getAssociatedPort())) {
                System.out.println("\033c");
                System.out.println("Vehicle does not exist in this port.");
                return;
            }
            System.out.println("Enter Container ID:");
            String unloadContainerId = reader.readLine();
            ContainerCRUD unloadContainerCRUD = new ContainerCRUD();
            Container currentUnloadContainer = unloadContainerCRUD.readContainerById(unloadContainerId);
            if (currentUnloadContainer == null) {
                System.out.println("\033c");
                System.out.println("The container does not exist.");
                return;
            }
            if (currentUnloadContainer.getStatus() == "Loaded") {
                System.out.println("\033c");
                System.out.println("The container is already loaded.");
                return;
            }
            if (!currentUnloadContainer.getLocationId().equals(currentUnloadVehicle.getId())) {
                System.out.println("\033c");
                System.out.println("The container are not on the vehicle.");
                return;
            }

            // getContainersList().add(container);
            double totalWeightInPort = 0.0;
            PortCRUD unloadPortCRUD = new PortCRUD();
            Port currentUnloadPort = unloadPortCRUD.readPort(currentUnloadVehicle.getCurrentPortId());
            List<Container> containersList = unloadContainerCRUD.readAllContainers();
            for (Container container : containersList) {
                if (container.getLocationId().equals(currentUnloadPort.getId())) {
                    totalWeightInPort += container.getWeight();
                }
            }
            if (currentUnloadContainer.getWeight() + totalWeightInPort <= currentUnloadPort
                    .getStoringCapacity()) {
                System.out.println("\033c");
                System.out.println("Unload successfully.");
                System.out.println(currentUnloadPort
                        .getStoringCapacity());
                unloadContainerCRUD.updateContainer(unloadContainerId,
                        new Container(unloadContainerId, currentUnloadContainer.getWeight(),
                                currentUnloadContainer.getType(), "OnWait", currentUnloadPort.getId()));
            } else {
                System.out.println("Too much weight to load this container to the current port!");
                return;
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void refuelContainer() {
        System.out.println("\033c");
        try {
            System.out.println("Refuel Vehicle");
            System.out.println("Enter Vehicle ID:");
            String refuelVehicleId = reader.readLine();
            Vehicle currentRefuelVehicle = crud.readVehicle(refuelVehicleId);
            if (currentRefuelVehicle == null) {
                System.out.println("\033c");
                System.out.println(
                        "Error: No vehicle with the entered ID exists. Please enter a valid ID.");
                return;
            }
            if (!currentRefuelVehicle.getCurrentPortId().equals(currentUser.getAssociatedPort())) {
                System.out.println("\033c");
                System.out.println("Vehicle does not exist in this port.");
                return;
            }
            System.out.println(currentRefuelVehicle.getId());
            VehicleCRUD currentRefuelVehicleCRUD = new VehicleCRUD();

            if (refuelVehicleId.startsWith("tr-")) {
                Truck newTruck = (Truck) currentRefuelVehicle;
                currentRefuelVehicleCRUD.updateVehicle(refuelVehicleId,
                        new Truck(newTruck.getId(), newTruck.getName(), newTruck.getFuelCapacity(),
                                newTruck.getCarryingCapacity(), newTruck.getFuelCapacity(),
                                newTruck.getTruckType(), newTruck.getCurrentPortId()));
                System.out.println("\033c");
                System.out.println("Refuel successfully");
            } else if (refuelVehicleId.startsWith("sh-")) {
                Ship newShip = (Ship) currentRefuelVehicle;
                currentRefuelVehicleCRUD.updateVehicle(refuelVehicleId,
                        new Ship(newShip.getId(), newShip.getName(), newShip.getFuelCapacity(),
                                newShip.getCarryingCapacity(), newShip.getFuelCapacity(),
                                newShip.getCurrentPortId()));
                System.out.println("\033c");
                System.out.println("Refuel successfully");
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void moveToPort() {
        System.out.println("\033c");
        try {
            System.out.println("Check if a vehicle can move to a port");
            System.out.println("Enter Vehicle ID:");
            String checkVehicleId = reader.readLine();
            Vehicle existingVehicle = crud.readVehicle(checkVehicleId);
            if (existingVehicle == null) {
                System.out.println("\033c");
                System.out.println(
                        "Error: No vehicle with the entered ID exists. Please enter a valid ID.");
                return;
            }
            if (!existingVehicle.getCurrentPortId().equals(currentUser.getAssociatedPort())) {
                System.out.println("\033c");
                System.out.println("Vehicle does not exist in this port.");
                return;
            }
            System.out.println("Enter Port ID:");
            String checkPortId = reader.readLine();
            Port currentPort = portCRUD.readPort(checkPortId);
            if (currentPort == null) {
                System.out.println(
                        "Error: The entered currentPortID does not match any port in the system.");
                return;
            }

            boolean canMove = crud.checkMoveToPort(checkVehicleId, checkPortId);
            if (canMove) {
                System.out.println("The vehicle can move to the specified port.");
                // Update the currentPort attribute of the vehicle in the file
                Vehicle vehicleToUpdate = crud.readVehicle(checkVehicleId);
                if (vehicleToUpdate != null) {
                    double requiredFuel = crud.calculateRequireFuel(checkVehicleId, checkPortId);
                    if (vehicleToUpdate.getId().startsWith("sh-")) {
                        Ship newShip = (Ship) existingVehicle;
                        crud.updateVehicle(checkVehicleId,
                                new Ship(newShip.getId(), newShip.getName(),
                                        newShip.getFuelCapacity(),
                                        newShip.getCarryingCapacity(),
                                        newShip.getCurrentFuel() - requiredFuel,
                                        checkPortId));
                    } else {
                        Truck newTruck = (Truck) existingVehicle;
                        crud.updateVehicle(checkVehicleId,
                                new Truck(newTruck.getId(), newTruck.getName(),

                                        newTruck.getFuelCapacity(),
                                        newTruck.getCarryingCapacity(),
                                        newTruck.getCurrentFuel() - requiredFuel,
                                        newTruck.getTruckType(),
                                        checkPortId));
                    }

                    System.out.println("Vehicle's current port has been updated to: " +
                            checkPortId);
                } else {
                    System.out.println("Error: Unable to find the vehicle with ID: " +
                            checkVehicleId);
                }
            } else {
                System.out.println("The vehicle cannot move to the specified port.");
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void displayVehicleCRUDMana() {

        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Display all vehicles");
            System.out.println("2. Update a vehicle in your port");
            System.out.println("3. Load Container");
            System.out.println("4. Unload Container");
            System.out.println("5. Refuel");
            System.out.println("6. Move to port");
            System.out.println("7. Exit");
            try {
                int choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        displayAllVehicle();
                        break;
                    case 2:
                        updateAVehicle();
                        break;
                    case 3:
                        loadContainer();
                        break;
                    case 4:
                        unloadContainer();
                        break;
                    case 5:
                        refuelContainer();
                        break;
                    case 6:
                        moveToPort();
                        break;
                    case 7:
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
