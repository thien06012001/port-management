package models.trip;

import java.util.Date;

import models.port.Port;
import models.vehicle.Vehicle;

public class Trip {
    protected Vehicle vehicle; // Might be accessed by subclasses for specific vehicle-related operations
    protected Port departurePort; // Important for calculating distances, costs, etc.
    protected Port arrivalPort; // Same reason as departurePort
    protected Date departureDate; // Could be used for scheduling or logging purposes in subclasses
    protected Date arrivalDate; // Same reason as departureDate
    private String status; // Status might not need direct modification in subclasses

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setDeparturePort(Port departurePort) {
        this.departurePort = departurePort;
    }

    public void setArrivalPort(Port arrivalPort) {
        this.arrivalPort = arrivalPort;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Port getDeparturePort() {
        return departurePort;
    }

    public Port getArrivalPort() {
        return arrivalPort;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

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