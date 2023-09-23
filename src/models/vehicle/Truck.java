package models.vehicle;

import models.container.Container;
import models.port.Port;

 public class Truck extends Vehicle {
    private String truckType; // Basic/Reefer/Tanker

    

    public Truck(String id, String name, double fuelCapacity, double carryingCapacity, double currentFuel, String truckType, String currentPortId) {
        super(id, name, "Truck", fuelCapacity, carryingCapacity, currentFuel, currentPortId);
        this.truckType = truckType;
    }

    @Override
    public void loadContainer(Container container) {
        boolean canLoad = false;
        switch (truckType) {
            case "Basic":
                if (container.getType().equals("DryStorage") || container.getType().equals("OpenTop") || container.getType().equals("OpenSide")) {
                    canLoad = true;
                } else {
                    System.out.println("Basic truck can't carry this container type: " + container.getType());
                }
                break;

            case "Reefer":
                if (container.getType().equals("Refrigerated")) {
                    canLoad = true;
                } else {
                    System.out.println("Reefer truck can't carry this container type: " + container.getType());
                }
                break;

            case "Tanker":
                if (container.getType().equals("Liquid")) {
                    canLoad = true;
                } else {
                    System.out.println("Tanker truck can't carry this container type: " + container.getType());
                }
                break;

            default:
                System.out.println("Unknown truck type: " + truckType);
                break;
        }

        if (canLoad) {
            if (container.getWeight() + getTotalWeight() <= getCarryingCapacity()) {
                getContainersList().add(container);
            } else {
                System.out.println("Too much weight to load this container!");
            }
        }
    }

    public String getTruckType() {
        return truckType;
    }
}
