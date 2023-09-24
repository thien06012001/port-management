package thiendev;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import CRUD.ContainerCRUD;
import CRUD.PortCRUD;
import models.container.Container;
import models.port.Port;
import thiendev.Vehicle;
import views.menu.Login;
import thiendev.Ship;
import thiendev.Truck;

public class VehicleCRUD {
    private static final String PORT_FILE_PATH = System.getProperty("user.dir") + "/src/database/Port.txt";
    private static final String VEHICLE_FILE_PATH = System.getProperty("user.dir") + "/src/database/Vehicle.txt";

    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/Vehicle.txt";
    private List<Container> containers = new ArrayList<>();
    private ContainerCRUD containerCRUD = new ContainerCRUD();

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
                        vehicle = new Truck(id, name, currentFuel, fuelCapacity, carryingCapacity, truckType,
                                currentPortId);
                    } else {
                        System.err.println("Invalid line for Truck: " + line);
                        continue;
                    }
                } else if ("Ship".equalsIgnoreCase(vehicleType)) {
                    String currentPortId = parts[6];
                    vehicle = new Ship(id, name, currentFuel, fuelCapacity, carryingCapacity, currentPortId);
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
                        + ", " + truck.getTruckType());
            } else {
                bw.write("\n" + vehicle.getId() + ", " + vehicle.getName() + ", " + vehicle.getType() + ", "
                        + vehicle.getFuelCapacity() + ", " + vehicle.getCarryingCapacity() + ", "
                        + vehicle.getCurrentFuel());
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
                            + truck.getCurrentFuel() + ", " + truck.getTruckType());
                } else {
                    bw.write("\n" + vehicle.getId() + ", " + vehicle.getName() + ", " + vehicle.getType() + ", "
                            + vehicle.getFuelCapacity() + ", " + vehicle.getCarryingCapacity() + ", "
                            + vehicle.getCurrentFuel());
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
                    String truckType = data[6].trim();
                    String currentPortId = data[7].trim();

                    // Check if the vehicle is a Truck or Ship and create the corresponding object
                    if (type.equals("Truck")) {
                        return new Truck(id, name, fuelCapacity, carryingCapacity, currentFuel, truckType,
                                currentPortId);
                    } else if (type.equals("Ship")) {
                        // Assuming Ship has a similar constructor
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
        if (vehicle == null || destinationPort == null) {
            return false;
        }

        // Calculate the distance from the vehicle's current port to the destination
        // port
        double distance = calculateDistance(departurePort, destinationPort);

        // Calculate the required fuel for the journey
        double requiredFuel = 0;
        for (Container c : vehicle.getContainersList()) {
            if (vehicle.getType().equals("Ship")) {
                requiredFuel += c.getFuelConsumptionForShip() * distance;
            } else if (vehicle.getType().equals("Truck")) {
                requiredFuel += c.getFuelConsumptionForTruck() * distance;
            }
        }

        // Check if the vehicle has enough fuel for the journey
        if (vehicle.getCurrentFuel() >= requiredFuel) {
            return true;
        } else {
            return false;
        }
    }
    public static double calculateDistance(Port startingPort, Port destinationPort) {
        final int R = 6371; 
        double latDistance = Math.toRadians(destinationPort.getLatitude() - startingPort.getLatitude());
        double lonDistance = Math.toRadians(destinationPort.getLongitude() - startingPort.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(startingPort.getLatitude())) * Math.cos(Math.toRadians(destinationPort.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
    public static void main(String[] args) {
        VehicleCRUD crud = new VehicleCRUD();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Login login = new Login();
        PortCRUD portCRUD = new PortCRUD();

        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Display all vehicles");
            System.out.println("2. Add a vehicle");
            System.out.println("3. Update a vehicle");
            System.out.println("4. Delete a vehicle");
            System.out.println("5. Exit");
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

                            System.out.println("Enter Vehicle Name:");
                            String name = reader.readLine();
                            System.out.println("Enter Vehicle Type (Truck/Ship):");
                            String type = reader.readLine();
                            System.out.println("Enter Fuel Capacity:");
                            double fuelCapacity = Double.parseDouble(reader.readLine());
                            System.out.println("Enter Carrying Capacity:");
                            double carryingCapacity = Double.parseDouble(reader.readLine());
                            System.out.println("Enter Current Fuel:");
                            double currentFuel = Double.parseDouble(reader.readLine());
                            System.out.println("Enter current port Id: ");
                            String currentPortId = reader.readLine();
                            Port currentPort = portCRUD.readPort(currentPortId);
                            if (currentPort == null) {
                                System.out.println("The port does not exist in the system.");
                                break;
                            }
                            if ("Truck".equalsIgnoreCase(type)) {
                                System.out.println("Enter Truck Type (e.g., Basic, Reefer, Tanker):");
                                String truckType = reader.readLine(); // Prompting the user for TruckType when adding a
                                                                      // new Truck
                                crud.addVehicle(new Truck(id, name, fuelCapacity, carryingCapacity, currentFuel,
                                        truckType, ""));

                            } else if ("Ship".equalsIgnoreCase(type)) {
                                crud.addVehicle(new Ship(id, name, fuelCapacity, carryingCapacity, currentFuel, ""));
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
                                crud.updateVehicle(updateId, new Truck(updateId, updateName, updateCurrentFuel,
                                        updateFuelCapacity, updateCarryingCapacity, updateTruckType, ""));
                            } else if ("Ship".equalsIgnoreCase(updateType)) {
                                crud.updateVehicle(updateId, new Ship(updateId, updateName, updateCurrentFuel,
                                        updateFuelCapacity, updateCarryingCapacity, ""));
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
                        System.out.println("Going back...");
                        System.out.print("\033c");
                        login.displayLogin();
                        return;
                    case 9:
                        System.out.println("Check if a vehicle can move to a port");
                        System.out.println("Enter Vehicle ID:");
                        String checkVehicleId = reader.readLine();
                        System.out.println("Enter Port ID:");
                        String checkPortId = reader.readLine();

                        boolean canMove = checkMoveToPort(checkVehicleId, checkPortId);
                        if (canMove) {
                            System.out.println("The vehicle can move to the specified port.");

                            // Update the currentPort attribute of the vehicle in the file
                            Vehicle vehicleToUpdate = crud.readVehicle(checkVehicleId);
                            if (vehicleToUpdate != null) {
                                vehicleToUpdate.setCurrentPortId(checkPortId);
                                crud.updateVehicle(checkVehicleId, vehicleToUpdate);
                                System.out.println("Vehicle's current port has been updated to: " + checkPortId);
                            } else {
                                System.out.println("Error: Unable to find the vehicle with ID: " + checkVehicleId);
                            }
                        } else {
                            System.out.println("The vehicle cannot move to the specified port.");
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}
