
import java.util.ArrayList;
import java.util.Collections;

public class Port implements IPort {
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private double storingCapacity;
    private boolean landingAbility;
    private ArrayList<Container> containersList = new ArrayList<>();
    private ArrayList<Vehicle> vehiclesList = new ArrayList<>();
    private ArrayList<Trip> trafficHistory = new ArrayList<>();

    // Constructor
    public Port(String id, String name, double latitude, double longitude, double storingCapacity,
            boolean landingAbility) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
    }

    // Calculate distance to another port using the Haversine formula
    public double calculateDistanceTo(Port otherPort) {
        final int R = 6371; // Radius of the Earth in kilometers

        double latDistance = Math.toRadians(otherPort.latitude - this.latitude);
        double lonDistance = Math.toRadians(otherPort.longitude - this.longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(otherPort.latitude))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    // Add a vehicle to the port
    public void addVehicle(Vehicle vehicle) {
        if (vehicle instanceof Truck && !landingAbility) {
            System.out.println("This port is not marked as landing. Trucks cannot be added.");
            return;
        }
        vehiclesList.add(vehicle);
        vehicle.setCurrentPort(this);
    }

    // Remove a vehicle from the port
    public void removeVehicle(Vehicle vehicle) {
        if (vehiclesList.contains(vehicle)) {
            vehiclesList.remove(vehicle);
            vehicle.setCurrentPort(null);
        }
    }

    // Add a container to the port
    public void addContainer(Container container) {
        if (getTotalWeight() + container.weight <= storingCapacity) {
            containersList.add(container);
        } else {
            System.out.println("Port capacity exceeded!");
        }
    }

    // Remove a container from the port
    public void removeContainer(Container container) {
        containersList.remove(container);
    }

    // Add a trip to the traffic history
    public void addTrip(Trip trip) {
        trafficHistory.add(trip);
    }

    // Calculate the total weight of all containers in the port
    public double getTotalWeight() {
        double totalWeight = 0;
        for (Container c : containersList) {
            totalWeight += c.weight;
        }
        return totalWeight;
    }

    // Getters for relevant attributes
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Vehicle> getVehiclesList() {
        return new ArrayList<>(vehiclesList);
    }

    public ArrayList<Container> getContainersList() {
        return new ArrayList<>(containersList);
    }

    public ArrayList<Trip> getTrafficHistory() {
        return new ArrayList<>(trafficHistory);
    }
}
