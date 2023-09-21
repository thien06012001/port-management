package model.vehicle;

public class Ship extends Vehicle {
    public Ship(String id, String name, double fuelCapacity, double carryingCapacity) {
        super(id, name, "Ship", fuelCapacity, carryingCapacity);
    }
}
