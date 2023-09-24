package models.container;

public class OpenTop extends Container {

    public OpenTop(String id, double weight,String status, String locationId) {
        super(id, weight, "OpenTop", status, locationId);
    }

    @Override
    public double getFuelConsumptionForShip() {
        return weight * 2.8;
    }

    @Override
    public double getFuelConsumptionForTruck() {
        return weight * 3.2;
    }
}