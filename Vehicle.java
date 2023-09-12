package portManagement;

import java.util.List;
import java.util.ArrayList;

public class Vehicle {
    private String id;
    private String name;
    private String type; // Ship/Truck
    private double currentFuel;
    private double carryingCapacity;
    private double fuelCapacity;
    private Port currentPort;
    private List<Container> containersList;

    // Constructor
    public Vehicle(String id, String name, String type, double fuelCapacity, double carryingCapacity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.fuelCapacity = fuelCapacity;
        this.carryingCapacity = carryingCapacity;
        this.currentFuel = fuelCapacity; // Assuming vehicle starts fully fueled
        this.containersList = new ArrayList<>();
    }

    // Load a container onto the vehicle
    public void loadContainer(Container container) {
        if (container.weight + getTotalWeight() <= carryingCapacity) {
            containersList.add(container);
        } else {
            System.out.println("Too much weight to load this container!");
        }
    }

    // Unload a container from the vehicle
    public void unloadContainer(Container container) {
        containersList.remove(container);
    }

    // Move the vehicle to a specified port
    public void moveToPort(Port destinationPort) {
        double distance = currentPort.calculateDistanceTo(destinationPort);
        double requiredFuel = 0;
        for (Container c : containersList) {
            if (type.equals("Ship")) {
                requiredFuel += c.getFuelConsumptionForShip() * distance;
            } else if (type.equals("Truck")) {
                requiredFuel += c.getFuelConsumptionForTruck() * distance;
            }
        }
        if (currentFuel >= requiredFuel) {
            currentFuel -= requiredFuel;
            currentPort = destinationPort;
        } else {
            System.out.println("Not enough fuel to move to the destination!");
        }
    }

    // Refuel the vehicle
    public void refuel(double amount) {
        if (currentFuel + amount <= fuelCapacity) {
            currentFuel += amount;
        } else {
            System.out.println("Fuel capacity exceeded! Filling up to maximum capacity.");
            currentFuel = fuelCapacity;
        }
    }

    // Calculate the total weight of containers on the vehicle
    public double getTotalWeight() {
        double totalWeight = 0;
        for (Container c : containersList) {
            totalWeight += c.weight;
        }
        return totalWeight;
    }

    // Add these methods
    public Port getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(Port port) {
        this.currentPort = port;
    }
}
