package model.container;

public class Container {
    String id;
    private double weight;
    String type; // DryStorage/OpenTop/OpenSide/Refrigerated/Liquid

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Container(String id, double weight, String type) {
        this.id = id;
        this.weight = weight;
        this.type = type;
    }

    double getFuelConsumptionForShip() {
        switch (type) {
            case "DryStorage":
                return weight * 3.5;
            case "OpenTop":
                return weight * 2.8;
            case "OpenSide":
                return weight * 2.7;
            case "Refrigerated":
                return weight * 4.5;
            case "Liquid":
                return weight * 4.8;
            default:
                return 0.0;
        }
    }

    double getFuelConsumptionForTruck() {
        switch (type) {
            case "DryStorage":
                return weight * 4.6;
            case "OpenTop":
                return weight * 3.2;
            case "OpenSide":
                return weight * 3.2;
            case "Refrigerated":
                return weight * 5.4;
            case "Liquid":
                return weight * 5.3;
            default:
                return 0.0;
        }
    }
}
