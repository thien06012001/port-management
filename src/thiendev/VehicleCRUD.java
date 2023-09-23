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
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}
