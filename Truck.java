package portManagement;

class Truck extends Vehicle {
    private String truckType; // Basic/Reefer/Tanker

    public Truck(String id, String name, double fuelCapacity, double carryingCapacity, double currentFuel, String truckType) {
        super(id, name, "Truck", fuelCapacity, carryingCapacity, currentFuel);
        this.truckType = truckType;
    }

    @Override
    public void loadContainer(Container container) {
        boolean canLoad = false;

        switch (truckType) {
            case "Basic":
                if (container.type.equals("DryStorage") || container.type.equals("OpenTop") || container.type.equals("OpenSide")) {
                    canLoad = true;
                } else {
                    System.out.println("Basic truck can't carry this container type: " + container.type);
                }
                break;

            case "Reefer":
                if (container.type.equals("Refrigerated")) {
                    canLoad = true;
                } else {
                    System.out.println("Reefer truck can't carry this container type: " + container.type);
                }
                break;

            case "Tanker":
                if (container.type.equals("Liquid")) {
                    canLoad = true;
                } else {
                    System.out.println("Tanker truck can't carry this container type: " + container.type);
                }
                break;

            default:
                System.out.println("Unknown truck type: " + truckType);
                break;
        }

        if (canLoad) {
            if (container.weight + getTotalWeight() <= getCarryingCapacity()) {
                getContainersList().add(container);
            } else {
                System.out.println("Too much weight to load this container!");
            }
        }
    }

    public static void main(String[] args) {
        // Creating a Truck instance using Vehicle reference
        Vehicle v1 = new Truck("tr-001", "BigTruck", 500, 2000, 400, "Basic");

        // Printing some details about the truck
        System.out.println(v1.getCarryingCapacity());

    }
}
