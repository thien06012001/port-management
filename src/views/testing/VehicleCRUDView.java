package views.testing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import CRUD.PortCRUD;
import CRUD.VehicleCRUD;
import models.port.Port;
import models.vehicle.Ship;
import models.vehicle.Truck;
import models.vehicle.Vehicle;
import views.menu.Login;

public class VehicleCRUDView {
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

                            if ("Truck".equalsIgnoreCase(type)) {
                                System.out.println("Enter Truck Type (e.g., Basic, Reefer, Tanker):");
                                String truckType = reader.readLine(); // Prompting the user for TruckType when adding a new Truck
                                crud.addVehicle(new Truck(id, name, fuelCapacity, carryingCapacity, currentFuel, truckType, ""));

                            } else if ("Ship".equalsIgnoreCase(type)) {
                                crud.addVehicle(new Ship(id, name, fuelCapacity, carryingCapacity, currentFuel, ""));
                            } else {
                                System.out.println("Invalid vehicle type!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number for fuel capacity, carrying capacity, or current fuel.");
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
                                crud.updateVehicle(updateId, new Truck(updateId, updateName, updateCurrentFuel, updateFuelCapacity, updateCarryingCapacity, updateTruckType, ""));
                            } else if ("Ship".equalsIgnoreCase(updateType)) {
                                crud.updateVehicle(updateId, new Ship(updateId, updateName, updateCurrentFuel, updateFuelCapacity, updateCarryingCapacity, ""));
                            } else {
                                System.out.println("Invalid vehicle type!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number for fuel capacity, carrying capacity, or current fuel.");
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
