package portManagement;

class Truck extends Vehicle {
    String truckType; // Basic/Reefer/Tanker

    public Truck(String id, String name, double fuelCapacity, double carryingCapacity, String truckType) {
        super(id, name, "Truck", fuelCapacity, carryingCapacity);
        this.truckType = truckType;
    }
}
