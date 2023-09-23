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
                    bw.write("\n" + vehicle.getId() + ", " + vehicle.getName() + ", " + vehicle.getType() + ", "
                            + vehicle.getFuelCapacity() + ", " + vehicle.getCarryingCapacity() + ", "
                            + vehicle.getCurrentFuel() + ", " + vehicle.getCurrentPortId());
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
                            container = new DryStorage(containerId, containerWeight, containerStatus, containerLocationId);
                            break;
                        case "Liquid":
                            container = new Liquid(containerId, containerWeight,  containerStatus, containerLocationId);
                            break;
                        case "OpenSide":
                            container = new OpenSide(containerId, containerWeight, containerStatus, containerLocationId);
                            break;
                        case "OpenTop":
                            container = new OpenTop(containerId, containerWeight, containerStatus, containerLocationId);
                            break;
                        case "Refrigerated":
                            container = new Refrigerated(containerId, containerWeight, containerStatus, containerLocationId);
                            break;
                        default:
                            container = new Container(containerId, containerWeight, containerType, containerStatus, containerLocationId);
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
            System.out.println("5. Load Container");
            System.out.println("6. Exit");
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
                                continue;
                            }
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
                        System.out.println("Enter Truck ID:");
                        String truckId = reader.readLine();
                        System.out.println("Enter Container ID:");
                        String containerId = reader.readLine();
                        crud.assignContainerToTruck(truckId, containerId);
                        break;    
                    case 6:
                        System.out.println("Going back...");
                        System.out.print("\033c");
                        login.displayLogin();
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
