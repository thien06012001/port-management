package models.port;

import models.vehicle.Vehicle;
import java.util.ArrayList;
import java.util.List;
import models.container.Container;
import models.trip.Trip;

public class Port {
    private String id;
    private String name;
    public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getStoringCapacity() {
		return storingCapacity;
	}

	public boolean getLandingAbility() {
		return landingAbility;
	}

	private double latitude;
    private double longitude;
    private double storingCapacity;
    private boolean landingAbility;
    private List<Container> containersList = new ArrayList<>();
    private List<Vehicle> vehiclesList = new ArrayList<>();
    private List<Trip> trafficHistory = new ArrayList<>();

    // Constructor
    public Port(String id, String name, double latitude, double longitude, double storingCapacity, boolean landingAbility) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
    }

    public Port() {
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
        vehiclesList.add(vehicle);
        vehicle.setCurrentPort(this);
    }

    // Remove a vehicle from the port
    public void removeVehicle(Vehicle vehicle) {
        vehiclesList.remove(vehicle);
        vehicle.setCurrentPort(null);
    }

    // Add a container to the port
    public void addContainer(Container container) {
        if (getTotalWeight() + container.getWeight() <= storingCapacity) {
            containersList.add(container);
        } else {
            System.out.println("Port capacity exceeded!");
        }
    }

    // Remove a container from the port
    public void removeContainer(Container container) {
        containersList.remove(container);
    }

    // Calculate the total weight of all containers in the port
    public double getTotalWeight() {
        double totalWeight = 0;
        for (Container c : containersList) {
            totalWeight += c.getWeight();
        }
        return totalWeight;
    }

    // Getters and setters for relevant attributes

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Vehicle> getVehiclesList() {
        return vehiclesList;
    }

    public List<Container> getContainersList() {
        return containersList;
    }

    public List<Trip> getTrafficHistory() {
        return trafficHistory;
    }
}
