public class Container {
    private String id;
    private long weight;
    private ContainerType type;

    public Container(String id, long weight, ContainerType type) {
        this.id = id;
        this.weight = weight;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public ContainerType getType() {
        return type;
    }

    public void setType(ContainerType type) {
        this.type = type;
    }

    // Method to calculate the required fuel for a specific container for a ship
    public double calculateShipFuelRequired(double distance) {
        double fuelRate;
        switch (type) {
            case DRY_STORAGE:
                fuelRate = 3.5;
                break;
            case OPEN_TOP:
                fuelRate = 2.8;
                break;
            case OPEN_SIDE:
                fuelRate = 2.7;
                break;
            case REFRIGERATED:
                fuelRate = 4.5;
                break;
            case LIQUID:
                fuelRate = 4.8;
                break;
            default:
                fuelRate = 0.0; // Default fuel rate if the container type is unknown
                break;
        }
        return fuelRate * weight * distance;
    }

    // Method to calculate the required fuel for a specific container for a truck
    public double calculateTruckFuelRequired(double distance) {
        double fuelRate;
        switch (type) {
            case DRY_STORAGE:
                fuelRate = 4.6;
                break;
            case OPEN_TOP:
                fuelRate = 3.2;
                break;
            case OPEN_SIDE:
                fuelRate = 3.2;
                break;
            case REFRIGERATED:
                fuelRate = 5.4;
                break;
            case LIQUID:
                fuelRate = 5.3;
                break;
            default:
                fuelRate = 0.0; // Default fuel rate if the container type is unknown
                break;
        }
        return fuelRate * weight * distance;
    }
}

enum ContainerType {
    DRY_STORAGE, OPEN_TOP, OPEN_SIDE, REFRIGERATED, LIQUID
}