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

    public boolean checkMoveToPort(String vehicleId, String portId) {
        // Load the vehicle and destination port using their IDs
        Vehicle vehicle = loadVehicleFromId(vehicleId);
        Port departurePort = loadPortFromId(vehicle.getCurrentPortId());
        System.out.println(departurePort);
        if(departurePort == null) {
            System.out.println("The vehicle is not in any port( Transporting container).");
            return false;
        }
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

    public double calculateRequireFuel(String vehicleId, String portId) {
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

    

}
