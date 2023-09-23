package models.container;

public class Refrigerated extends Container {

    public Refrigerated(String id, double weight,String status, String locationId) {
        super(id, weight, "Refrigerated", status, locationId);
    }

    @Override
    public double getFuelConsumptionForShip() {
        return weight * 4.5;
    }

    @Override
    public double getFuelConsumptionForTruck() {
        return weight * 5.4;
    }
}