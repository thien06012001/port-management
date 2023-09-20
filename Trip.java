package portManagement;

import java.util.Date;

public class Trip {
    protected Vehicle vehicle; // Might be accessed by subclasses for specific vehicle-related operations
    protected Port departurePort; // Important for calculating distances, costs, etc.
    protected Port arrivalPort; // Same reason as departurePort
    protected Date departureDate; // Could be used for scheduling or logging purposes in subclasses
    protected Date arrivalDate; // Same reason as departureDate
    private String status; // Status might not need direct modification in subclasses

    // Constructor
    public Trip(Vehicle vehicle, Port departurePort, Port arrivalPort, Date departureDate, Date arrivalDate, String status) {
        this.vehicle = vehicle;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.status = status;
    }

    // Getter and Setter for status, as it's private
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
