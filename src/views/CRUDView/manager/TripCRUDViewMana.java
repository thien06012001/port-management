package views.CRUDView.manager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import CRUD.PortCRUD;
import CRUD.TripCRUD;
import CRUD.UserCRUD;
import CRUD.VehicleCRUD;
import models.trip.Trip;
import models.user.User;

public class TripCRUDViewMana {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    static TripCRUD crud = new TripCRUD();
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static VehicleCRUD vehicleCRUD = new VehicleCRUD();
    static PortCRUD portCRUD = new PortCRUD();
   

    public static void displayAllTrip() {
        System.out.println("\033c");
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
            System.out.println("2. Calculate Daily Fuel");
            System.out.println("3. Display trip in the given day");
            System.out.println("4. List all trips from day A to day B");
            System.out.println("5. Back");

            try {
                int choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        displayAllTrip();
                        break;
                    case 2:
                        calculateDailyFuel();
                        break;
                    case 3:
                        crud.listTripsOnGivenDay();
                        break;
                    case 4:
                        crud.listTripsFromDayAToDayB();
                        break;
                    case 5:
                        System.out.println("Going back...");
                        System.out.print("\033c");
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
