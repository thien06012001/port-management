package views.CRUDView.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import CRUD.PortCRUD;
import CRUD.TripCRUD;
import CRUD.VehicleCRUD;
import models.port.Port;
import models.trip.Trip;
import models.vehicle.Ship;
import models.vehicle.Truck;
import models.vehicle.Vehicle;

public class TripCRUDView {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    static TripCRUD crud = new TripCRUD();
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static VehicleCRUD vehicleCRUD = new VehicleCRUD();
    static PortCRUD portCRUD = new PortCRUD();

    public static void displayAllTrip() {
        System.out.println("\033c");
        List<Trip> trips = crud.readAllTrips();
        for (Trip trip : trips) {
            System.out.println("Trip: " + trip.getTripId());
            System.out.println("Vehicle: " + trip.getVehicle().getName());
            System.out.println("Departure Port: " + trip.getDeparturePort().getName());
            System.out.println("Arrival Port: " + trip.getArrivalPort().getName());
            System.out.println("Departure Date: " + DATE_FORMAT.format(trip.getDepartureDate()));
            System.out.println("Arrival Date: " + DATE_FORMAT.format(trip.getArrivalDate()));
            System.out.println("Status: " + trip.getStatus());
            System.out.println();
        }
    }

    public static void addATrip() {
        System.out.println("\033c");
        try {
            System.out.println("Enter Trip ID:");
            String tripId = reader.readLine();
            Trip trip = crud.readTripById(tripId);
            if (trip != null) {
                System.out.println("\033c");
                System.out.println("The trip is already exist.");
                return;
            }
            System.out.println("Enter Vehicle ID:");
            String vehicleId = reader.readLine();
            Vehicle vehicle = vehicleCRUD.readVehicle(vehicleId);
            if (vehicle == null) {
                System.out.println("\033c");
                System.out.println("Vehicle not found!");
                return;
            }

            // Check if the vehicle's current port matches the departure port
            System.out.println("Enter Departure Port ID:");
            String depPortId = reader.readLine();
            Port departurePort = portCRUD.readPort(depPortId);

            if (departurePort == null) {
                System.out.println("\033c");
                System.out.println("Departure port not found!");
                return;
            }
            String currentPortId = vehicle.getCurrentPortId();
            // Assuming the last attribute in the vehicle details from Vehicle.txt is the
            // current port ID

            // Adjusted index to 7 based on the format of Vehicle.txt
            if (!currentPortId.equals(depPortId)) {
                System.out.println("\033c");
                System.out.println(
                        "Departure port is wrong. It should be the same as the vehicle's current port.");
                return;
            }

            System.out.println("Enter Arrival Port ID:");
            String arrPortId = reader.readLine();
            Port arrivalPort = portCRUD.readPort(arrPortId);
            if (arrivalPort == null) {
                System.out.println("\033c");
                System.out.println("Arrival port not found!");
                return;
            }

            // Check if the vehicle is a truck
            if (vehicleId.startsWith("tr-")) {
                // Check landing ability for truck
                if (!arrivalPort.getLandingAbility()) {
                    System.out.println("\033c");
                    System.out.println("Trucks cannot be utilized for ports without landing ability!");
                    return;
                }
            }

            System.out.println("Enter Departure Date (dd-MM-yyyy):");
            Date departureDate = DATE_FORMAT.parse(reader.readLine());
            System.out.println("Enter Arrival Date (dd-MM-yyyy):");
            Date arrivalDate = DATE_FORMAT.parse(reader.readLine());
            System.out.println("Enter Status:");
            String status = reader.readLine();
            if (!status.equals("Completed") && !status.equals("InProgress") && !status.equals("Scheduled")) {
                System.out.println("Invalid status.");
                return;
            }
            Trip newTrip = new Trip(tripId, vehicle, departurePort, arrivalPort, departureDate, arrivalDate,
                    status);
            crud.addTrip(newTrip);
            if (status.equals("Completed")) {
                if (vehicle.getType().equals("Truck")) {
                    Truck truck = (Truck) vehicle;
                    vehicleCRUD.updateVehicle(vehicleId,
                            new Truck(vehicleId, truck.getName(), truck.getCurrentFuel(),
                                    truck.getFuelCapacity(), truck.getCarryingCapacity(),
                                    truck.getTruckType(), arrPortId));
                }
                if (vehicle.getType() == "Ship") {
                    vehicleCRUD.updateVehicle(vehicleId,
                            new Ship(vehicleId, vehicle.getName(), vehicle.getCurrentFuel(),
                                    vehicle.getFuelCapacity(), vehicle.getCarryingCapacity(), arrPortId));
                }
            }

            else if (status.equals("InProgress")) {
                if (vehicle.getType().equals("Truck")) {
                    Truck truck = (Truck) vehicle;
                    vehicleCRUD.updateVehicle(vehicleId,
                            new Truck(vehicleId, truck.getName(), truck.getCurrentFuel(),
                                    truck.getFuelCapacity(), truck.getCarryingCapacity(),
                                    truck.getTruckType(), "none"));
                }
                if (vehicle.getType() == "Ship") {
                    vehicleCRUD.updateVehicle(vehicleId,
                            new Ship(vehicleId, vehicle.getName(), vehicle.getCurrentFuel(),
                                    vehicle.getFuelCapacity(), vehicle.getCarryingCapacity(), "none"));
                }
            }

            else if (status.equals("Scheduled")) {
                if (vehicle.getType().equals("Truck")) {
                    Truck truck = (Truck) vehicle;
                    vehicleCRUD.updateVehicle(vehicleId,
                            new Truck(vehicleId, truck.getName(), truck.getCurrentFuel(),
                                    truck.getFuelCapacity(), truck.getCarryingCapacity(),
                                    truck.getTruckType(), depPortId));
                }
                if (vehicle.getType() == "Ship") {
                    vehicleCRUD.updateVehicle(vehicleId,
                            new Ship(vehicleId, vehicle.getName(), vehicle.getCurrentFuel(),
                                    vehicle.getFuelCapacity(), vehicle.getCarryingCapacity(), depPortId));
                }
            } else {
                System.out.println("Status Invalid");
            }

            System.out.println("\033c");
            System.out.println("Trip added successfully!");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void updateATrip() {
        System.out.println("\033c");
        try {
            System.out.println("Enter trip Id:");
            String updateTripId = reader.readLine();
            Trip tripToUpdate = crud.readTripById(updateTripId);
            if (tripToUpdate == null) {
                System.out.println("\033c");
                System.out.println("Trip not found!");
                return;
            }
            System.out.println("Enter Vehicle ID to update:");
            String updateVehicleId = reader.readLine();

            VehicleCRUD vehicleCRUD = new VehicleCRUD();
            Vehicle vehicleToUpdate = vehicleCRUD.readVehicle(updateVehicleId);
            if (vehicleToUpdate == null) {
                System.out.println("\033c");
                System.out.println("Vehicle not found!");
                return;
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
            if (!newStatus.equals("Completed") && !newStatus.equals("InProgress") && !newStatus.equals("Scheduled")) {
                System.out.println("Invalid status.");
                return;
            }
            // Check the type of vehicle based on the VehicleID prefix and create the
            // appropriate instance
            Vehicle updatedVehicle;
            if (updateVehicleId.startsWith("tr-")) {
                Truck truckToUpdate = (Truck) vehicleToUpdate;
                updatedVehicle = new Truck(truckToUpdate.getId(), truckToUpdate.getName(),
                        truckToUpdate.getCurrentFuel(), truckToUpdate.getFuelCapacity(),
                        truckToUpdate.getCarryingCapacity(), truckToUpdate.getTruckType(),
                        vehicleToUpdate.getCurrentPortId());
            } else if (updateVehicleId.startsWith("sh-")) {
                Ship shipToUpdate = (Ship) vehicleToUpdate;
                updatedVehicle = new Ship(shipToUpdate.getId(), shipToUpdate.getName(),
                        shipToUpdate.getCurrentFuel(), shipToUpdate.getFuelCapacity(),
                        shipToUpdate.getCarryingCapacity(), vehicleToUpdate.getCurrentPortId());
            } else {
                System.out.println("\033c");
                System.out.println("Invalid vehicle type!");
                return;
            }

            Trip updatedTrip = new Trip(updateTripId, updatedVehicle, newDeparturePort, newArrivalPort,
                    newDepartureDate,
                    newArrivalDate, newStatus);
            crud.updateTrip(updateTripId, updatedTrip);
            System.out.println("\033c");
            System.out.println("Trip updated successfully!");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void deleteATrip() {
        System.out.println("\033c");
        try {
            System.out.println("Enter trip ID to delete:");
            String deleteTripId = reader.readLine();
            Trip trip = crud.readTripById(deleteTripId);
            if (trip == null) {
                System.out.println("\033c");
                System.out.println("The trip is not exist.");
                return;
            }
            crud.deleteTrip(deleteTripId);
            System.out.println("Trip deleted successfully!");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void calculateDailyFuel() {
        System.out.println("\033c");
        try {
            System.out.println("Enter Vehicle Id:");
            String calculatedVehicleId = reader.readLine();
            TripCRUD trip = new TripCRUD();
            List<Trip> tripList = trip.readAllTrips();
            for (Trip selectedTrip : tripList) {
                if (selectedTrip.getVehicle().getId().equals(calculatedVehicleId)) {
                    double requiredFuel = crud.calculateRequireFuel(selectedTrip.getDeparturePort().getId(),
                            selectedTrip.getArrivalPort().getId(), calculatedVehicleId);
                    Date departureDateTime = selectedTrip.getDepartureDate();
                    Date arrivalDateTime = selectedTrip.getArrivalDate();
                    // Calculate the time difference in milliseconds
                    long durationInMillis = arrivalDateTime.getTime() - departureDateTime.getTime();
                    // Convert duration to double representing days
                    double days = durationInMillis / (1000.0 * 60 * 60 * 24);
                    System.out.println("\033c");
                    System.out.println("Fuel use in a day: " + requiredFuel / days);
                    System.out.println("Total amount of fuel: " + requiredFuel);
                    System.out.println("Total days: " + days);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public void displayTripCRUDView() {

        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Display all trips");
            System.out.println("2. Add a trip");
            System.out.println("3. Update a trip");
            System.out.println("4. Delete a trip");
            System.out.println("5. Calculate Daily Fuel");
            System.out.println("6. Display trip in the given day");
            System.out.println("7. List all trips from day A to day B");
            System.out.println("8. Back");

            try {
                int choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║          All Trip          ║");
                        System.out.println("╚════════════════════════════╝");
                        displayAllTrip();
                        break;
                    case 2:
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║          Add Trip          ║");
                        System.out.println("╚════════════════════════════╝");
                        addATrip();
                        break;

                    case 3:
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║         Update Trip        ║");
                        System.out.println("╚════════════════════════════╝");
                        updateATrip();
                        break;
                    case 4:
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║         Delete Trip        ║");
                        System.out.println("╚════════════════════════════╝");
                        deleteATrip();
                        break;
                    case 5:
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║         Daily Fuel         ║");
                        System.out.println("╚════════════════════════════╝");
                        calculateDailyFuel();
                        break;
                    case 6:
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║          List Trip         ║");
                        System.out.println("╚════════════════════════════╝");
                        crud.listTripsOnGivenDay();
                        break;

                    case 7:
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║     List Trip In Period    ║");
                        System.out.println("╚════════════════════════════╝");
                        crud.listTripsFromDayAToDayB();
                        break;
                    case 8:
                        System.out.println("Going back...");
                        System.out.print("\033c");
                        return;
                    default:
                        System.out.println("\033c");
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
