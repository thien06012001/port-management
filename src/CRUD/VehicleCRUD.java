package CRUD;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import CRUD.PortCRUD;
import models.port.Port;
import models.vehicle.Vehicle;
import views.menu.Login;
import models.vehicle.Ship;
import models.vehicle.Truck;
import models.container.Container;
import models.container.DryStorage;
import models.container.Liquid;
import models.container.OpenSide;
import models.container.OpenTop;
import models.container.Refrigerated;

public class VehicleCRUD {

    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/Vehicle.txt";
    private static final String PORT_FILE_PATH = System.getProperty("user.dir") + "/src/database/Port.txt";
    private static final String CONTAINER_FILE_PATH = System.getProperty("user.dir") + "/src/database/Container.txt";
    private static List<String> portIds = new ArrayList<>();
    private static List<Container> containers = new ArrayList<>();
    private ContainerCRUD containerCRUD = new ContainerCRUD();
    private static final String VEHICLE_FILE_PATH = System.getProperty("user.dir") + "/src/database/Vehicle.txt";

    static {
        loadPortData();

    }

    public List<Vehicle> readAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();

        try {
            File file = new File(FILE_PATH);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Skip comment lines and empty lines
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(", ");
                if (parts.length < 6) {
                    System.err.println("Invalid line: " + line);
                    continue;
                }

                String id = parts[0];
                String name = parts[1];
                String vehicleType = parts[2];
                double fuelCapacity = Double.parseDouble(parts[3]);
                double carryingCapacity = Double.parseDouble(parts[4]);
                double currentFuel = Double.parseDouble(parts[5]);

                Vehicle vehicle = null;

                if ("Truck".equalsIgnoreCase(vehicleType)) {
                    if (parts.length > 6) { // Check if the truckType attribute exists
                        String truckType = parts[6];
                        String currentPortId = parts[7];
                        vehicle = new Truck(id, name, fuelCapacity, carryingCapacity, currentFuel, truckType,
                                currentPortId);
                    } else {
                        System.err.println("Invalid line for Truck: " + line);
                        continue;
                    }
                } else if ("Ship".equalsIgnoreCase(vehicleType)) {
                    String currentPortId = parts[6];
                    vehicle = new Ship(id, name, fuelCapacity, carryingCapacity, currentFuel, currentPortId);
                }
                if (vehicle != null) {
                    vehicles.add(vehicle);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        return vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            if (vehicle instanceof Truck) {
                Truck truck = (Truck) vehicle;
                bw.write("\n" + truck.getId() + ", " + truck.getName() + ", " + truck.getType() + ", "
                        + truck.getFuelCapacity() + ", " + truck.getCarryingCapacity() + ", " + truck.getCurrentFuel()
                        + ", " + truck.getTruckType() + ", " + truck.getCurrentPortId());
            } else {
                bw.write("\n" + vehicle.getId() + ", " + vehicle.getName() + ", " + vehicle.getType() + ", "
                        + vehicle.getFuelCapacity() + ", " + vehicle.getCarryingCapacity() + ", "
                        + vehicle.getCurrentFuel() + ", " + vehicle.getCurrentPortId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateVehicle(String id, Vehicle updatedVehicle) {
        List<Vehicle> vehicles = readAllVehicles();
        int index = -1;
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            vehicles.set(index, updatedVehicle);
            writeAllVehicles(vehicles);
        }
    }

    public void deleteVehicle(String id) {
        List<Vehicle> vehicles = readAllVehicles();
        vehicles.removeIf(vehicle -> vehicle.getId().equals(id));
        writeAllVehicles(vehicles);
    }

    private void writeAllVehicles(List<Vehicle> vehicles) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("# Vehicle File\n# Format: ID, Name, Type, Fuel Capacity, Carrying Capacity, Current Fuel");
            for (Vehicle vehicle : vehicles) {
                if (vehicle instanceof Truck) {
                    Truck truck = (Truck) vehicle;
                    bw.write("\n" + truck.getId() + ", " + truck.getName() + ", " + truck.getType() + ", "
                            + truck.getFuelCapacity() + ", " + truck.getCarryingCapacity() + ", "
                            + truck.getCurrentFuel() + ", " + truck.getTruckType() + ", " + truck.getCurrentPortId());
                } else {
                    Ship ship = (Ship) vehicle;
                    bw.write("\n" + ship.getId() + ", " + ship.getName() + ", " + ship.getType() + ", "
                            + ship.getFuelCapacity() + ", " + ship.getCarryingCapacity() + ", "
                            + ship.getCurrentFuel() + ", " + ship.getCurrentPortId());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vehicle readVehicle(String vehicleId) {
        List<Vehicle> vehicles = readAllVehicles();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId().equals(vehicleId)) {
                return vehicle;
            }
        }
        return null; // Return null if no vehicle with the given ID is found
    }

    private static void loadPortData() {
        try (BufferedReader br = new BufferedReader(new FileReader(PORT_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {
                    String[] parts = line.split(", ");
                    portIds.add(parts[0].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadContainerData() {
        try (BufferedReader br = new BufferedReader(new FileReader(CONTAINER_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {
                    String[] parts = line.split(", ");
                    String containerId = parts[0].trim();
                    double containerWeight = Double.parseDouble(parts[1].trim());
                    String containerType = parts[2].trim();
                    String containerStatus = parts[3].trim();
                    String containerLocationId = parts[4].trim();

                    Container container = null;
                    switch (containerType) {
                        case "DryStorage":
                            container = new DryStorage(containerId, containerWeight, containerStatus,
                                    containerLocationId);
                            break;
                        case "Liquid":
                            container = new Liquid(containerId, containerWeight, containerStatus, containerLocationId);
                            break;
                        case "OpenSide":
                            container = new OpenSide(containerId, containerWeight, containerStatus,
                                    containerLocationId);
                            break;
                        case "OpenTop":
                            container = new OpenTop(containerId, containerWeight, containerStatus, containerLocationId);
                            break;
                        case "Refrigerated":
                            container = new Refrigerated(containerId, containerWeight, containerStatus,
                                    containerLocationId);
                            break;
                        default:
                            container = new Container(containerId, containerWeight, containerType, containerStatus,
                                    containerLocationId);
                            break;
                    }
                    containers.add(container);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void assignContainerToTruck(String truckId, String containerId) {
        Truck truck = (Truck) readVehicle(truckId);
        if (truck != null) {
            Container container = getContainerById(containerId);
            if (container != null) {
                if (truck.getCarryingCapacity() >= container.getWeight()) {
                    truck.loadContainer(container);
                    updateVehicle(truckId, truck);
                } else {
                    System.out.println("The truck's carrying capacity is insufficient for this container.");
                }
            } else {
                System.out.println("Container not found.");
            }
        } else {
            System.out.println("Truck not found.");
        }
    }

    private Container getContainerById(String containerId) {
        ContainerCRUD containerCRUD = new ContainerCRUD();
        List<Container> containerList = containerCRUD.readAllContainers();
        for (Container container : containerList) {
            if (container.getId().equals(containerId)) {
                return container;
            }
        }
        return null;
    }

    public int countContainersForVehicle(String vehicleId) {
        List<Vehicle> vehicles = readAllVehicles();

        List<Container> containers = containerCRUD.readAllContainers();
        int containerCount = 0;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId().equals(vehicleId)) {
                for (Container container : containers) {
                    if (container.getLocationId().equals(vehicleId)) {
                        containerCount++;
                    }
                }
                break; // No need to continue once the vehicle is found
            }
        }

        return containerCount;
    }

    public int countSpecificContainersForVehicle(String vehicleId, String containerType) {
        List<Container> containers = containerCRUD.readAllContainers();
        int count = 0;
        for (Container container : containers) {
            if (container.getLocationId().equals(vehicleId) && container.getType().equalsIgnoreCase(containerType)) {
                count++;
            }
        }
        return count;
    }

    // Define the file path for the Vehicle.txt file

    public static Vehicle loadVehicleFromId(String vehicleId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(VEHICLE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip comments or empty lines
                if (line.startsWith("#") || line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");
                if (data[0].trim().equals(vehicleId)) {
                    // Extracting data from the file
                    String id = data[0].trim();
                    String name = data[1].trim();
                    String type = data[2].trim();
                    double fuelCapacity = Double.parseDouble(data[3].trim());
                    double carryingCapacity = Double.parseDouble(data[4].trim());
                    double currentFuel = Double.parseDouble(data[5].trim());

                    // Check if the vehicle is a Truck or Ship and create the corresponding object
                    if (type.equals("Truck")) {
                        String truckType = data[6].trim();
                        String currentPortId = data[7].trim();

                        return new Truck(id, name, fuelCapacity, carryingCapacity, currentFuel, truckType,
                                currentPortId);
                    } else if (type.equals("Ship")) {
                        // Assuming Ship has a similar constructor
                        String currentPortId = data[6].trim();

                        return new Ship(id, name, fuelCapacity, carryingCapacity, currentFuel, currentPortId);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Port loadPortFromId(String portId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PORT_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip comments or empty lines
                if (line.startsWith("#") || line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");
                if (data[0].trim().equals(portId)) {
                    // Extracting data from the file
                    String id = data[0].trim();
                    String name = data[1].trim();
                    double latitude = Double.parseDouble(data[2].trim());
                    double longitude = Double.parseDouble(data[3].trim());
                    double storingCapacity = Double.parseDouble(data[4].trim());
                    boolean landingAbility = Boolean.parseBoolean(data[5].trim());

                    // Create and return the Port object
                    return new Port(id, name, latitude, longitude, storingCapacity, landingAbility);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkMoveToPort(String vehicleId, String portId) {
        // Load the vehicle and destination port using their IDs
        Vehicle vehicle = loadVehicleFromId(vehicleId);
        Port departurePort = loadPortFromId(vehicle.getCurrentPortId());
        Port destinationPort = loadPortFromId(portId);

        // If either the vehicle or port couldn't be loaded, return false
        // if (vehicle == null || destinationPort == null) {
        // return false;
        // }

        // Calculate the distance from the vehicle's current port to the destination
        // port
        double distance = calculateDistance(departurePort, destinationPort);

        // Calculate the required fuel for the journey
        double requiredFuel = 0;
        ContainerCRUD crud = new ContainerCRUD();
        List<Container> containerList = crud.readAllContainers();
        for (Container c : containerList) {
            if (c.getLocationId().equals(vehicleId)) {
                if (vehicle.getType().equals("Ship")) {
                    // requiredFuel += c.getFuelConsumptionForShip() * distance;
                    if (c.getType().equals("DryStorage")) {

                        requiredFuel += c.getWeight() * 3.5 * distance;
                    } else if (c.getType().equals("Liquid")) {

                        requiredFuel += c.getWeight() * 4.8 * distance;
                    } else if (c.getType().equals("OpenSide")) {

                        requiredFuel += c.getWeight() * 2.7 * distance;
                    } else if (c.getType().equals("OpenTop")) {

                        requiredFuel += c.getWeight() * 2.8 * distance;
                    } else if (c.getType().equals("Refrigerated")) {

                        requiredFuel += c.getWeight() * 4.5 * distance;
                    }
                } else if (vehicle.getType().equals("Truck")) {
                    if (c.getType().equals("DryStorage")) {

                        requiredFuel += c.getWeight() * 4.6 * distance;
                    } else if (c.getType().equals("Liquid")) {

                        requiredFuel += c.getWeight() * 5.3 * distance;
                    } else if (c.getType().equals("openSide")) {

                        requiredFuel += c.getWeight() * 3.2 * distance;
                    } else if (c.getType().equals("OpenTop")) {

                        requiredFuel += c.getWeight() * 3.2 * distance;
                    } else if (c.getType().equals("Refrigerated")) {

                        requiredFuel += c.getWeight() * 5.4 * distance;
                    }
                }
            }
            // else {
            // requiredFuel = 100;
            // }

        }
        if (requiredFuel == 0) {
            requiredFuel += vehicle.getCarryingCapacity() / 100;
        }
        // Check if the vehicle has enough fuel for the journey
        if (vehicle.getCurrentFuel() >= requiredFuel) {

            return true;
        } else {

            return false;
        }
    }

    public static double calculateRequireFuel(String vehicleId, String portId) {
        // Load the vehicle and destination port using their IDs
        Vehicle vehicle = loadVehicleFromId(vehicleId);
        Port departurePort = loadPortFromId(vehicle.getCurrentPortId());
        Port destinationPort = loadPortFromId(portId);
        // Calculate the distance from the vehicle's current port to the destination
        // port
        double distance = calculateDistance(departurePort, destinationPort);

        // Calculate the required fuel for the journey
        double requiredFuel = 0;
        ContainerCRUD crud = new ContainerCRUD();
        List<Container> containerList = crud.readAllContainers();
        for (Container c : containerList) {
            if (c.getLocationId().equals(vehicleId)) {
                if (vehicle.getType().equals("Ship")) {
                    // requiredFuel += c.getFuelConsumptionForShip() * distance;
                    if (c.getType().equals("DryStorage")) {

                        requiredFuel += c.getWeight() * 3.5 * distance;
                    } else if (c.getType().equals("Liquid")) {

                        requiredFuel += c.getWeight() * 4.8 * distance;
                    } else if (c.getType().equals("OpenSide")) {

                        requiredFuel += c.getWeight() * 2.7 * distance;
                    } else if (c.getType().equals("OpenTop")) {

                        requiredFuel += c.getWeight() * 2.8 * distance;
                    } else if (c.getType().equals("Refrigerated")) {

                        requiredFuel += c.getWeight() * 4.5 * distance;
                    }
                } else if (vehicle.getType().equals("Truck")) {
                    if (c.getType().equals("DryStorage")) {

                        requiredFuel += c.getWeight() * 4.6 * distance;
                    } else if (c.getType().equals("Liquid")) {

                        requiredFuel += c.getWeight() * 5.3 * distance;
                    } else if (c.getType().equals("openSide")) {

                        requiredFuel += c.getWeight() * 3.2 * distance;
                    } else if (c.getType().equals("OpenTop")) {

                        requiredFuel += c.getWeight() * 3.2 * distance;
                    } else if (c.getType().equals("Refrigerated")) {

                        requiredFuel += c.getWeight() * 5.4 * distance;
                    }
                }
            }
            // else {
            // requiredFuel = 100;
            // }

        }
        if (requiredFuel == 0) {
            requiredFuel += vehicle.getCarryingCapacity() / 100;
        }
        return requiredFuel;
    }

    public static double calculateDistance(Port startingPort, Port destinationPort) {
        final int R = 6371;
        double latDistance = Math.toRadians(destinationPort.getLatitude() - startingPort.getLatitude());
        double lonDistance = Math.toRadians(destinationPort.getLongitude() - startingPort.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(startingPort.getLatitude()))
                        * Math.cos(Math.toRadians(destinationPort.getLatitude()))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public static void main(String[] args) {
        VehicleCRUD crud = new VehicleCRUD();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PortCRUD portCRUD = new PortCRUD();
        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Display all vehicles");
            System.out.println("2. Add a vehicle");
            System.out.println("3. Update a vehicle");
            System.out.println("4. Delete a vehicle");
            System.out.println("5. Load Container");
            System.out.println("6. Unload Container");
            System.out.println("7. Refuel");
            System.out.println("8. Move to port");
            System.out.println("9. Exit");
            try {
                int choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
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
                        break;
                    case 2:
                        try {
                            System.out.println("Enter Vehicle ID:");
                            String id = reader.readLine();
                            // Check if the entered vehicleId already exists in the Vehicle.txt file
                            Vehicle existingVehicle = crud.readVehicle(id);
                            if (existingVehicle != null) {
                                System.out.println(
                                        "Error: A vehicle with the entered ID already exists. Please enter a unique ID.");
                                break;
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
                                continue;
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
                        break;
                    case 3:
                        try {
                            System.out.println("Enter Vehicle ID to update:");
                            String updateId = reader.readLine();
                            // Check if the entered vehicleId exists in the Vehicle.txt file
                            Vehicle existingVehicle = crud.readVehicle(updateId);
                            if (existingVehicle == null) {
                                System.out.println(
                                        "Error: No vehicle with the entered ID exists. Please enter a valid ID.");
                                continue;
                            }

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
                                        new Truck(updateId, updateName, updateCurrentFuel, updateFuelCapacity,
                                                updateCarryingCapacity, updateTruckType,
                                                existingVehicle.getCurrentPortId()));
                            } else if ("Ship".equalsIgnoreCase(updateType)) {
                                crud.updateVehicle(updateId,
                                        new Ship(updateId, updateName, updateCurrentFuel, updateFuelCapacity,
                                                updateCarryingCapacity, existingVehicle.getCurrentPortId()));
                            } else {
                                System.out.println("Invalid vehicle type!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(
                                    "Please enter a valid number for fuel capacity, carrying capacity, or current fuel.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.println("Enter Vehicle ID to delete:");
                        String deleteId = reader.readLine();
                        crud.deleteVehicle(deleteId);
                        break;

                    case 5:
                        System.out.println("Assign a container to a truck");
                        System.out.println("Enter Vehicle ID:");
                        String vehicleId = reader.readLine();
                        Vehicle currentVehicle = crud.readVehicle(vehicleId);
                        if (currentVehicle == null) {
                            System.out.println("The vehicle does not exist.");
                            break;
                        }
                        System.out.println("Enter Container ID:");
                        String containerId = reader.readLine();
                        ContainerCRUD containerCRUD = new ContainerCRUD();
                        Container currentContainer = containerCRUD.readContainerById(containerId);
                        if (currentContainer == null) {
                            System.out.println("The container does not exist.");
                            break;
                        }
                        if (currentContainer.getStatus() == "Loaded") {
                            System.out.println("The container is already loaded.");
                            break;
                        }
                        if (!currentContainer.getLocationId().equals(currentVehicle.getCurrentPortId())) {
                            System.out.println("The container and the vehicle are not on the same port.");
                            break;
                        }
                        if (vehicleId.startsWith("tr-")) {
                            Truck truck = (Truck) currentVehicle;
                            String truckType = truck.getTruckType();
                            boolean canLoad = false;
                            switch (truckType) {
                                case "Basic":
                                    if (currentContainer.getType().equals("DryStorage")
                                            || currentContainer.getType().equals("OpenTop")
                                            || currentContainer.getType().equals("OpenSide")) {
                                        canLoad = true;
                                    } else {
                                        System.out.println(
                                                "Basic truck can't carry this container type: "
                                                        + currentContainer.getType());
                                    }
                                    break;

                                case "Reefer":
                                    if (currentContainer.getType().equals("Refrigerated")) {
                                        canLoad = true;
                                    } else {
                                        System.out.println(
                                                "Reefer truck can't carry this container type: "
                                                        + currentContainer.getType());
                                    }
                                    break;

                                case "Tanker":
                                    if (currentContainer.getType().equals("Liquid")) {
                                        canLoad = true;
                                    } else {
                                        System.out.println(
                                                "Tanker truck can't carry this container type: "
                                                        + currentContainer.getType());
                                    }
                                    break;

                                default:
                                    System.out.println("Unknown truck type: " + truckType);
                                    break;
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
                                } else {
                                    System.out.println("Too much weight to load this container!");
                                    System.out.println(currentVehicle.getCarryingCapacity());
                                    break;
                                }
                            }
                        }
                        break;
                    case 6:
                        System.out.println("Unload container from vehicle");
                        System.out.println("Enter Vehicle ID:");
                        String unloadVehicleId = reader.readLine();
                        Vehicle currentUnloadVehicle = crud.readVehicle(unloadVehicleId);
                        if (currentUnloadVehicle == null) {
                            System.out.println("The vehicle does not exist.");
                            break;
                        }
                        System.out.println("Enter Container ID:");
                        String unloadContainerId = reader.readLine();
                        ContainerCRUD unloadContainerCRUD = new ContainerCRUD();
                        Container currentUnloadContainer = unloadContainerCRUD.readContainerById(unloadContainerId);
                        if (currentUnloadContainer == null) {
                            System.out.println("The container does not exist.");
                            break;
                        }
                        if (currentUnloadContainer.getStatus() == "Loaded") {
                            System.out.println("The container is already loaded.");
                            break;
                        }
                        if (!currentUnloadContainer.getLocationId().equals(currentUnloadVehicle.getId())) {
                            System.out.println("The container are not on the vehicle.");
                            break;
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
                            System.out.println("Unload successfully.");
                            System.out.println(currentUnloadPort
                                    .getStoringCapacity());
                            unloadContainerCRUD.updateContainer(unloadContainerId,
                                    new Container(unloadContainerId, currentUnloadContainer.getWeight(),
                                            currentUnloadContainer.getType(), "OnWait", currentUnloadPort.getId()));
                        } else {
                            System.out.println("Too much weight to load this container to the current port!");
                            break;
                        }

                        break;
                    case 7:
                        System.out.println("Refuel Vehicle");
                        System.out.println("Enter Vehicle ID:");
                        String refuelVehicleId = reader.readLine();
                        Vehicle currentRefuelVehicle = crud.readVehicle(refuelVehicleId);
                        System.out.println(currentRefuelVehicle.getId());
                        VehicleCRUD currentRefuelVehicleCRUD = new VehicleCRUD();
                        if (currentRefuelVehicle == null) {
                            System.out.println("The vehicle does not exist.");
                            break;
                        }
                        if (refuelVehicleId.startsWith("tr-")) {
                            Truck newTruck = (Truck) currentRefuelVehicle;
                            currentRefuelVehicleCRUD.updateVehicle(refuelVehicleId,
                                    new Truck(newTruck.getId(), newTruck.getName(), newTruck.getFuelCapacity(),
                                            newTruck.getCarryingCapacity(), newTruck.getFuelCapacity(),
                                            newTruck.getTruckType(), newTruck.getCurrentPortId()));
                        } else if (refuelVehicleId.startsWith("sh-")) {
                            Ship newShip = (Ship) currentRefuelVehicle;
                            currentRefuelVehicleCRUD.updateVehicle(refuelVehicleId,
                                    new Ship(newShip.getId(), newShip.getName(), newShip.getFuelCapacity(),
                                            newShip.getCarryingCapacity(), newShip.getFuelCapacity(),
                                            newShip.getCurrentPortId()));
                        }

                        break;

                    case 8:
                        System.out.println("Check if a vehicle can move to a port");
                        System.out.println("Enter Vehicle ID:");
                        String checkVehicleId = reader.readLine();
                        Vehicle existingVehicle = crud.readVehicle(checkVehicleId);
                        if (existingVehicle == null) {
                            System.out.println(
                                    "There is no vehicle with this ID in the system");
                            break;
                        }
                        System.out.println("Enter Port ID:");
                        String checkPortId = reader.readLine();
                        Port currentPort = portCRUD.readPort(checkPortId);
                        if (currentPort == null) {
                            System.out.println(
                                    "Error: The entered currentPortID does not match any port in the system.");
                            continue;
                        }

                        boolean canMove = checkMoveToPort(checkVehicleId, checkPortId);
                        if (canMove) {
                            System.out.println("The vehicle can move to the specified port.");
                            // Update the currentPort attribute of the vehicle in the file
                            Vehicle vehicleToUpdate = crud.readVehicle(checkVehicleId);
                            if (vehicleToUpdate != null) {
                                double requiredFuel = calculateRequireFuel(checkVehicleId, checkPortId);
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
                        break;
                    case 9:
                        System.out.println("Going back...");
                        System.out.print("\033c");
                        views.menu.Login.displayLogin();
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
