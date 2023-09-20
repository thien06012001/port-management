
import java.util.ArrayList;

public abstract class Vehicle implements IVehicle {
    private String id;
    private String name;
    private String type; // Ship/Truck
    private double currentFuel;
    private double carryingCapacity;
    private double fuelCapacity;
    private Port currentPort;
    private ArrayList<Container> containersList;

    // Constructor
    public Vehicle(String id, String name, String type, double fuelCapacity, double carryingCapacity,
            double currentFuel) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.fuelCapacity = fuelCapacity;
        this.carryingCapacity = carryingCapacity;
        this.currentFuel = currentFuel; // Assuming vehicle starts fully fueled
        this.containersList = new ArrayList<>();
    }

    // Getter methods
    public double getCarryingCapacity() {
        return carryingCapacity;
    }

    public ArrayList<Container> getContainersList() {
        return containersList;
    }

    // Abstract method to load a container onto the vehicle
    public abstract void loadContainer(Container container);

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
                requiredFuel = requiredFuel + c.getFuelConsumptionForShip() * distance;
            } else if (type.equals("Truck")) {
                requiredFuel = requiredFuel + c.getFuelConsumptionForTruck() * distance;
            }
        }
        if (currentFuel >= requiredFuel) {
            currentFuel = currentFuel - requiredFuel;
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

    // For refactor currentPort variable every time moving to another port
    public void setCurrentPort(Port port) {
        this.currentPort = port;
    }

    public Port getCurrentPort() {
        return currentPort;
    }
}
