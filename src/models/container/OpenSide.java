package models.container;

public class OpenSide extends Container {

    public OpenSide(String id, double weight,String status, String locationId) {
        super(id, weight, "OpenSide", status, locationId);
    }

    @Override
    public double getFuelConsumptionForShip() {
        return weight * 2.7;
    }

    @Override
    public double getFuelConsumptionForTruck() {
        return weight * 3.2;
    }
}