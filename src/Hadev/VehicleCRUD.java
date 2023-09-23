// package Hadev;

// import java.io.*;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Scanner;

// import models.vehicle.Ship;
// import models.vehicle.Truck;
// import models.vehicle.Vehicle;
// import views.menu.Login;

// public class VehicleCRUD {

//     private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/Vehicle.txt";

//     public List<Vehicle> readAllVehicles() {
//         List<Vehicle> vehicles = new ArrayList<>();

//         try {
//             File file = new File(FILE_PATH); // Replace with your file path
//             Scanner scanner = new Scanner(file);
//             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//             while (scanner.hasNextLine()) {
//                 String line = scanner.nextLine().trim();

//                 // Skip comment lines and empty lines
//                 if (line.startsWith("#") || line.isEmpty()) {
//                     continue;
//                 }

//                 String[] parts = line.split(", ");
//                 if (parts.length !=3 ) {
//                     System.err.println("Invalid line: " + line);
//                     continue;
//                 }

//                 if (parts.length == 6) {
//                     // Ship
//                     String id = parts[0];
//                     String name = parts[1];
//                     String type = parts[2];
//                     double fuelCapacity = Double.parseDouble(parts[3]);
//                     double carryingCapacity = Double.parseDouble(parts[4]);
//                     double currentFuel = Double.parseDouble(parts[5]);
//                     Vehicle ship = new Ship(id, name, fuelCapacity, carryingCapacity, currentFuel);
//                     vehicles.add(ship);
                    
//                 }

//                     if (parts.length == 7) {
//                     // Truck
//                     String id = parts[0];
//                     String name = parts[1];
//                     String type = parts[2];
//                     double fuelCapacity = Double.parseDouble(parts[3]);
//                     double carryingCapacity = Double.parseDouble(parts[4]);
//                     double currentFuel = Double.parseDouble(parts[5]);
//                     String truckType = parts[6];
//                     Vehicle truck = new Truck(id, name, fuelCapacity, carryingCapacity, currentFuel, truckType);
//                     vehicles.add(truck);
//                 }
//             }

//             scanner.close();
//         } catch (FileNotFoundException e) {
//             System.err.println("File not found: " + e.getMessage());
//         }
//         return vehicles;
//     }
//     // id, name, "Truck", fuelCapacity, carryingCapacity, currentFuel
//                 // id, name, "Ship", fuelCapacity, carryingCapacity, currentFuel

//                 // # Format: ID, Name, Type, Fuel Capacity, Carrying Capacity, Current Fuel, [Truck Type if it's a truck]

//     public void addShip(Ship ship) {
//         try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
//             bw.write("\n" + ship.getId() + ", " + ship.getName() + ", " + ship.getType() + ", " + ship.getFuelCapacity() + ", " + ship.getCarryingCapacity() +", " + ship.getCurrentFuel());
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     public void addTruck(Truck truck) {
//         try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
//             bw.write("\n" + truck.getId() + ", " + truck.getName() + ", " + truck.getType() + ", " + truck.getFuelCapacity() + ", " + truck.getCarryingCapacity() +", " + truck.getCurrentFuel() + ", " + truck.getTruckType());
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     public void updateShip(String id, Ship updatedShip) {
//         List<Vehicle> vehicles = readAllVehicles();
//         int index = -1;
//         for (int i = 0; i < vehicles.size(); i++) {
//             if (vehicles.get(i).getId().equals(id)) {
//                 index = i;
//                 break;
//             }
//         }
//         if (index != -1) {
//             vehicles.set(index, updatedShip);
//             writeAllVehicles(vehicles);
//         }
//     }

//     public void updateTruck(String id, Truck updatedTruck) {
//         List<Vehicle> vehicles = readAllVehicles();
//         int index = -1;
//         for (int i = 0; i < vehicles.size(); i++) {
//             if (vehicles.get(i).getId().equals(id)) {
//                 index = i;
//                 break;
//             }
//         }
//         if (index != -1) {
//             vehicles.set(index, updatedTruck);
//             writeAllVehicles(vehicles);
//         }

//     }

//     public void deleteVehicle(String id) {
//         List<Vehicle> vehicles = readAllVehicles();
//         vehicles.removeIf(vehicle -> vehicle.getId().equals(id));
//         writeAllVehicles(vehicles);
//     }

//     private void writeAllVehicles(List<Vehicle> vehicles) {
//         try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
//             bw.write("// # Format: ID, Name, Type, Fuel Capacity, Carrying Capacity, Current Fuel, [Truck Type if it's a truck]");
//             bw.write("#Ship: ");
//             for (Vehicle ship : vehicles) {
//                 if (ship instanceof Ship) {
//                     bw.write("\n" + ship.getId() + ", " + ship.getName() + ", " + ship.getType() + ", " + ship.getFuelCapacity() + ", " + ship.getCarryingCapacity() +", " + ship.getCurrentFuel());
//                 } 
//             }
//             bw.write("#Truck: ");
//             for (Vehicle truck : vehicles) {
//                 if (truck instanceof Truck) {
//                     bw.write("\n" + truck.getId() + ", " + truck.getName() + ", " + truck.getType() + ", " + truck.getFuelCapacity() + ", " + truck.getCarryingCapacity() +", " + truck.getCurrentFuel() + ", " + ((Truck)truck).getTruckType());
                    
//                 }
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     public static void main(String[] args) {
//         VehicleCRUD crud = new VehicleCRUD();
//         BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//         while (true) {
//             System.out.println("Choose an operation:");
//             System.out.println("1. Display all vehicles");
//             System.out.println("2. Add a ship");
//             System.out.println("3. Add a truck");
//             System.out.println("4. Delete a ship");
//             System.out.println("5. Delete a truck");
//             System.out.println("6. Update a ship");
//             System.out.println("7. Update a truck");
//             System.out.println("8. Exit");
//             try {
//                 int choice = Integer.parseInt(reader.readLine());
//                 switch (choice) {
//                     case 1:
//                         List<Vehicle> vehicles = crud.readAllVehicles();
//                         // # Format: ID, Name, Type, Fuel Capacity, Carrying Capacity, Current Fuel, [Truck Type if it's a truck]
//                         for (Vehicle vehicle : vehicles) {
//                             System.out.println("Vehicle ID: " + vehicle.getId());
//                             System.out.println("Weight: " + vehicle.getName());
//                             System.out.println("Type: " + vehicle.getType());
//                             System.out.println("Fuel Capacity: " + vehicle.getFuelCapacity());
//                             System.out.println("Carrying Capacity: " + vehicle.getCarryingCapacity());
//                             System.out.println("Current Fuel: " + vehicle.getCurrentFuel());
//                             System.out.println();
//                         }
//                         break;
//                     // case 2:
//                     //     System.out.println("Enter Container ID:");
//                     //     String id = reader.readLine();
//                     //     System.out.println("Enter Container Weight:");
//                     //     double weight = Double.parseDouble(reader.readLine());
//                     //     System.out.println("Enter Container Type:");
//                     //     String type = reader.readLine();
//                     //     crud.addContainer(new Container(id, weight, type));
//                     //     break;
//                     // case 3:
//                     //     System.out.println("Enter Container ID to update:");
//                     //     String updateId = reader.readLine();
//                     //     System.out.println("Enter new Container Weight:");
//                     //     double updateWeight = Double.parseDouble(reader.readLine());
//                     //     System.out.println("Enter new Container Type:");
//                     //     String updateType = reader.readLine();
//                     //     crud.updateContainer(updateId, new Container(updateId, updateWeight, updateType));
//                     //     break;
//                     // case 4:
//                     //     System.out.println("Enter Container ID to delete:");
//                     //     String deleteId = reader.readLine();
//                     //     crud.deleteContainer(deleteId);
//                     //     break;
//                     // case 5:
//                     //     System.out.println("Going back...");
//                     //     System.out.print("\033c");
//                     //     menu.Login.displayLogin(); // Call the displayLogin method from the Login class
//                     //     return;
//                     default:
//                         System.out.println("Invalid choice. Please try again.");
//                 }
//             } catch (Exception e) {
//                 System.out.println("Error: " + e.getMessage());
//             }
//         }
//     }
// }
