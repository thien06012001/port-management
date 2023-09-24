package models.container;

public class DryStorage extends Container {

    public DryStorage(String id, double weight,String status, String locationId) {
        super(id, weight, "DryStorage", status, locationId);
    }

    @Override
    public double getFuelConsumptionForShip() {
        return weight * 3.5;
    }

    @Override
    public double getFuelConsumptionForTruck() {
        return weight * 4.6;
    }
}