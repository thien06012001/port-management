package models.vehicle;

import models.container.Container;
import models.port.Port;

public class Ship extends Vehicle {

    public Ship(String id, String name, double fuelCapacity, double carryingCapacity, double currentFuel, String currentPortId) {
        super(id, name, "Ship", fuelCapacity, carryingCapacity, currentFuel, currentPortId);
    }

    @Override
    public void loadContainer(Container container) {
        if (container.getWeight() + getTotalWeight() <= getCarryingCapacity()) {
            getContainersList().add(container);
        } else {
            System.out.println("Too much weight to load this container!");
        }
    }
}
