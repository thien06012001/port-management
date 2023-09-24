package CRUD;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import CRUD.PortCRUD;
import CRUD.VehicleCRUD;
import views.menu.Login;
import models.trip.Trip;
import models.vehicle.Vehicle;
import models.container.Container;
import models.port.Port;
import models.vehicle.Ship;
import models.vehicle.Truck;

public class TripCRUD {

    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/Trip.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

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

    public double calculateRequireFuel(String departurePortId, String arrivalPortId, String vehicleId) {
        VehicleCRUD crud = new VehicleCRUD();
        // Load the vehicle and destination port using their IDs
        Vehicle vehicle = crud.loadVehicleFromId(vehicleId);
        Port departurePort = crud.loadPortFromId(departurePortId);
        Port destinationPort = crud.loadPortFromId(arrivalPortId);
        // Calculate the distance from the vehicle's current port to the destination
        // port
        double distance = calculateDistance(departurePort, destinationPort);

        // Calculate the required fuel for the journey
        double requiredFuel = 0;
        ContainerCRUD containerCRUD = new ContainerCRUD();
        List<Container> containerList = containerCRUD.readAllContainers();
        for (Container c : containerList) {
            if (c.getLocationId().equals(vehicleId)) {
                if (vehicle.getType().equals("Ship")) {
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
        }
        if (requiredFuel == 0) {
            requiredFuel += vehicle.getCarryingCapacity() / 100;
        }
        return requiredFuel;
    }

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
                            vehicle.getFuelCapacity(), vehicle.getCarryingCapacity(), ((Truck) vehicle).getTruckType(),
                            vehicle.getCurrentPortId());
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
        VehicleCRUD vehicleCRUD = new VehicleCRUD();
        String vehicleId = trip.getVehicle().getId();
        String departurePortId = trip.getDeparturePort().getId();

        // Check if the vehicle can move to the departure port
        if (vehicleCRUD.checkMoveToPort(vehicleId, departurePortId)) {
            // The vehicle can move to the destination port, so proceed to add the trip
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                bw.write("\n" + trip.getVehicle().getId() + ", " + trip.getDeparturePort().getId() + ", "
                        + trip.getArrivalPort().getId() + ", "
                        + DATE_FORMAT.format(trip.getDepartureDate()) + ", " + DATE_FORMAT.format(trip.getArrivalDate())
                        + ", " + trip.getStatus());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("The vehicle cannot move to the destination port with its current fuel.");
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

    public void listTripsOnGivenDay() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the date (dd-MM-yyyy) to list trips:");
        try {
            String givenDateStr = reader.readLine();
            Date givenDate = DATE_FORMAT.parse(givenDateStr);

            List<Trip> trips = readAllTrips();
            boolean foundTrips = false;

            System.out.println("Trips on " + givenDateStr + ":");
            for (Trip trip : trips) {
                if (trip.getDepartureDate().equals(givenDate)) {
                    foundTrips = true;
                    System.out.println("Vehicle: " + trip.getVehicle().getName());
                    System.out.println("Departure Port: " + trip.getDeparturePort().getName());
                    System.out.println("Arrival Port: " + trip.getArrivalPort().getName());
                    System.out.println("Departure Date: " + DATE_FORMAT.format(trip.getDepartureDate()));
                    System.out.println("Arrival Date: " + DATE_FORMAT.format(trip.getArrivalDate()));
                    System.out.println("Status: " + trip.getStatus());
                    System.out.println();
                }
            }

            if (!foundTrips) {
                System.out.println("No trips found on the given date.");
            }

        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
    }

    public void listTripsFromDayAToDayB() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter the start date (dd-MM-yyyy):");
            Date startDate = DATE_FORMAT.parse(reader.readLine());

            System.out.println("Enter the end date (dd-MM-yyyy):");
            Date endDate = DATE_FORMAT.parse(reader.readLine());

            List<Trip> trips = readAllTrips();
            boolean found = false;
            for (Trip trip : trips) {
                if (!trip.getDepartureDate().before(startDate) && !trip.getDepartureDate().after(endDate)) {
                    System.out.println("Vehicle: " + trip.getVehicle().getName());
                    System.out.println("Departure Port: " + trip.getDeparturePort().getName());
                    System.out.println("Arrival Port: " + trip.getArrivalPort().getName());
                    System.out.println("Departure Date: " + DATE_FORMAT.format(trip.getDepartureDate()));
                    System.out.println("Arrival Date: " + DATE_FORMAT.format(trip.getArrivalDate()));
                    System.out.println("Status: " + trip.getStatus());
                    System.out.println();
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No trips found between the given dates.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
