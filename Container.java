
public class Container {
    private String id;
    protected double weight;
    protected String type; // DryStorage/OpenTop/OpenSide/Refrigerated/Liquid

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
