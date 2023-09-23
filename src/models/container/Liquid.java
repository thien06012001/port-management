package models.container;

public class Liquid extends Container {

    public Liquid(String id, double weight,String status, String locationId) {
        super(id, weight, "Liquid", status, locationId);
    }

    @Override
    public double getFuelConsumptionForShip() {
        return weight * 4.8;
    }

    @Override
    public double getFuelConsumptionForTruck() {
        return weight * 5.3;
    }
}