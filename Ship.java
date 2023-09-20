
public class Ship extends Vehicle {

    public Ship(String id, String name, double fuelCapacity, double carryingCapacity, double currentFuel) {
        super(id, name, "Ship", fuelCapacity, carryingCapacity, currentFuel);
    }

    @Override
    public void loadContainer(Container container) {
        if (container.weight + getTotalWeight() <= getCarryingCapacity()) {
            getContainersList().add(container);
        } else {
            System.out.println("Too much weight to load this container!");
        }
    }
}
