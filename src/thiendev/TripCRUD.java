package thiendev;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import CRUD.PortCRUD;
import thiendev.VehicleCRUD;
import views.menu.Login;
import thiendev.Trip;
import thiendev.Vehicle;
import models.port.Port;
import thiendev.Ship;
import thiendev.Truck;

public class TripCRUD {

    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/Trip.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public List<Trip> readAllTrips() {
        List<Trip> trips = new ArrayList<>();
        VehicleCRUD vehicleCRUD = new VehicleCRUD();
        PortCRUD portCRUD = new PortCRUD();

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
                if (parts.length != 6) {
                    System.err.println("Invalid line: " + line);
                    continue;
                }

                String vehicleID = parts[0];
                Vehicle vehicle = vehicleCRUD.readVehicle(vehicleID); // Look up the vehicle details

                if (vehicle == null) {
                    System.err.println("Vehicle with ID " + vehicleID + " not found.");
                    continue;
                }

                // Check the type of vehicle and create the appropriate instance
                if ("Truck".equalsIgnoreCase(vehicle.getType())) {
                    vehicle = new Truck(vehicle.getId(), vehicle.getName(), vehicle.getCurrentFuel(),
                            vehicle.getFuelCapacity(), vehicle.getCarryingCapacity(), ((Truck) vehicle).getTruckType(), vehicle.getCurrentPortId());
                } else if ("Ship".equalsIgnoreCase(vehicle.getType())) {
                    vehicle = new Ship(vehicle.getId(), vehicle.getName(), vehicle.getCurrentFuel(),
                            vehicle.getFuelCapacity(), vehicle.getCarryingCapacity(), vehicle.getCurrentPortId());
                }

                Port departurePort = portCRUD.readPort(parts[1]);
                Port arrivalPort = portCRUD.readPort(parts[2]);
                Date departureDate = DATE_FORMAT.parse(parts[3]);
                Date arrivalDate = DATE_FORMAT.parse(parts[4]);
                String status = parts[5];

                Trip trip = new Trip(vehicle, departurePort, arrivalPort, departureDate, arrivalDate, status);
                trips.add(trip);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
        }
        return trips;
    }

    public void addTrip(Trip trip) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write("\n" + trip.getVehicle().getId() + ", " + trip.getDeparturePort().getName() + ", "
                    + trip.getArrivalPort().getName() + ", "
                    + DATE_FORMAT.format(trip.getDepartureDate()) + ", " + DATE_FORMAT.format(trip.getArrivalDate())
                    + ", " + trip.getStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAllTrips(List<Trip> trips) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write(
                    "# Trip File\n# Format: Vehicle ID, Departure Port, Arrival Port, Departure Date, Arrival Date, Status");
            for (Trip trip : trips) {
                bw.write("\n" + trip.getVehicle().getId() + ", " + trip.getDeparturePort().getName() + ", "
                        + trip.getArrivalPort().getName() + ", " + DATE_FORMAT.format(trip.getDepartureDate())
                        + ", " + DATE_FORMAT.format(trip.getArrivalDate()) + ", " + trip.getStatus());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTrip(String vehicleId, Trip updatedTrip) {
        List<Trip> trips = readAllTrips();
        int index = -1;
        for (int i = 0; i < trips.size(); i++) {
            if (trips.get(i).getVehicle().getId().equals(vehicleId)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            trips.set(index, updatedTrip);
            writeAllTrips(trips);
        } else {
            System.out.println("Trip with vehicle ID " + vehicleId + " not found.");
        }
    }

    public void deleteTrip(String vehicleId) {
        List<Trip> trips = readAllTrips();
        trips.removeIf(trip -> trip.getVehicle().getId().equals(vehicleId));
        writeAllTrips(trips);
    }

    public static void main(String[] args) {
        TripCRUD crud = new TripCRUD();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        VehicleCRUD vehicleCRUD = new VehicleCRUD();
        PortCRUD portCRUD = new PortCRUD();
        Login login = new Login();

        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Display all trips");
            System.out.println("2. Add a trip");
            System.out.println("3. Update a trip");
            System.out.println("4. Delete a trip");
            System.out.println("5. Back");

            try {
                int choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        List<Trip> trips = crud.readAllTrips();
                        for (Trip trip : trips) {
                            System.out.println("Vehicle: " + trip.getVehicle().getName());
                            System.out.println("Departure Port: " + trip.getDeparturePort().getName());
                            System.out.println("Arrival Port: " + trip.getArrivalPort().getName());
                            System.out.println("Departure Date: " + DATE_FORMAT.format(trip.getDepartureDate()));
                            System.out.println("Arrival Date: " + DATE_FORMAT.format(trip.getArrivalDate()));
                            System.out.println("Status: " + trip.getStatus());
                            System.out.println();
                        }
                        break;
                    case 2:
                        System.out.println("Enter Vehicle ID:");
                        String vehicleId = reader.readLine();
                        Vehicle vehicle = vehicleCRUD.readVehicle(vehicleId);
                        if (vehicle == null) {
                            System.out.println("Vehicle not found!");
                            break;
                        }

                        // Check if the vehicle's current port matches the departure port
                        System.out.println("Enter Departure Port ID:");
                        String depPortId = reader.readLine();
                        Port departurePort = portCRUD.readPort(depPortId);

                        if (departurePort == null) {
                            System.out.println("Departure port not found!");
                            break;
                        }
                        String currentPortId = vehicle.getCurrentPortId();
                        // Assuming the last attribute in the vehicle details from Vehicle.txt is the
                        // current port ID
                       
                        // Adjusted index to 7 based on the format of Vehicle.txt
                        if (!currentPortId.equals(depPortId)) {
                            System.out.println(
                                    "Departure port is wrong. It should be the same as the vehicle's current port.");
                            break;
                        }

                        System.out.println("Enter Arrival Port ID:");
                        String arrPortId = reader.readLine();
                        Port arrivalPort = portCRUD.readPort(arrPortId);
                        if (arrivalPort == null) {
                            System.out.println("Arrival port not found!");
                            break;
                        }

                        // Check if the vehicle is a truck
                        if (vehicleId.startsWith("tr-")) {
                            // Check landing ability for truck
                            if (!departurePort.getLandingAbility() || !arrivalPort.getLandingAbility()) {
                                System.out.println("Trucks cannot be utilized for ports without landing ability!");
                                break;
                            }
                        }

                        System.out.println("Enter Departure Date (dd-MM-yyyy):");
                        Date departureDate = DATE_FORMAT.parse(reader.readLine());
                        System.out.println("Enter Arrival Date (dd-MM-yyyy):");
                        Date arrivalDate = DATE_FORMAT.parse(reader.readLine());
                        System.out.println("Enter Status:");
                        String status = reader.readLine();

                        Trip newTrip = new Trip(vehicle, departurePort, arrivalPort, departureDate, arrivalDate,
                                status);
                        crud.addTrip(newTrip);
                        System.out.println("Trip added successfully!");
                        break;

                    case 3:
                        System.out.println("Enter Vehicle ID to update:");
                        String updateVehicleId = reader.readLine();
                        Vehicle vehicleToUpdate = vehicleCRUD.readVehicle(updateVehicleId);
                        if (vehicleToUpdate == null) {
                            System.out.println("Vehicle not found!");
                            break;
                        }

                        System.out.println("Enter New Departure Port Name:");
                        String newDepPortName = reader.readLine();
                        Port newDeparturePort = portCRUD.readPort(newDepPortName);

                        System.out.println("Enter New Arrival Port Name:");
                        String newArrPortName = reader.readLine();
                        Port newArrivalPort = portCRUD.readPort(newArrPortName);

                        System.out.println("Enter New Departure Date (dd-MM-yyyy):");
                        Date newDepartureDate = DATE_FORMAT.parse(reader.readLine());

                        System.out.println("Enter New Arrival Date (dd-MM-yyyy):");
                        Date newArrivalDate = DATE_FORMAT.parse(reader.readLine());

                        System.out.println("Enter New Status:");
                        String newStatus = reader.readLine();

                        // Check the type of vehicle based on the VehicleID prefix and create the
                        // appropriate instance
                        Vehicle updatedVehicle;
                        if (updateVehicleId.startsWith("tr-")) {
                            Truck truckToUpdate = (Truck) vehicleToUpdate;
                            updatedVehicle = new Truck(truckToUpdate.getId(), truckToUpdate.getName(),
                                    truckToUpdate.getCurrentFuel(), truckToUpdate.getFuelCapacity(),
                                    truckToUpdate.getCarryingCapacity(), truckToUpdate.getTruckType(), vehicleToUpdate.getCurrentPortId());
                        } else if (updateVehicleId.startsWith("sh-")) {
                            Ship shipToUpdate = (Ship) vehicleToUpdate;
                            updatedVehicle = new Ship(shipToUpdate.getId(), shipToUpdate.getName(),
                                    shipToUpdate.getCurrentFuel(), shipToUpdate.getFuelCapacity(),
                                    shipToUpdate.getCarryingCapacity(), vehicleToUpdate.getCurrentPortId());
                        } else {
                            System.out.println("Invalid vehicle type!");
                            break;
                        }

                        Trip updatedTrip = new Trip(updatedVehicle, newDeparturePort, newArrivalPort, newDepartureDate,
                                newArrivalDate, newStatus);
                        crud.updateTrip(updateVehicleId, updatedTrip);
                        System.out.println("Trip updated successfully!");

                        break;
                    case 4:
                        System.out.println("Enter Vehicle ID to delete:");
                        String deleteVehicleId = reader.readLine();
                        crud.deleteTrip(deleteVehicleId);
                        System.out.println("Trip deleted successfully!");
                        break;
                    case 5:
                        System.out.println("Going back...");
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
