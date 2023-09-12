package portManagement;

import java.util.Date;

public class Trip {
    private Vehicle vehicle;
    private Date departureDate;
    private Date arrivalDate;
    private Port departurePort;
    private Port arrivalPort;
    private String status; // e.g. "Departed", "Arrived", "In Transit"

    // Constructor
    public Trip(Vehicle vehicle, Date departureDate, Port departurePort, Port arrivalPort) {
        this.vehicle = vehicle;
        this.departureDate = departureDate;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.status = "Scheduled"; // Default status when a trip is created
    }

    // Update the status of the trip
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // Getters and setters for relevant attributes

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public Port getDeparturePort() {
        return departurePort;
    }

    public Port getArrivalPort() {
        return arrivalPort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
