public class Vehicle {
    private String id;
    private String name;
    private long currentFuel;
    private long carryingCapacity;
    private long fuelCapacity;
    private Port currentPort;
    private int totalContainers;
    private int dryStorageContainers;
    private int openTopContainers;
    private int openSideContainers;
    private int refrigeratedContainers;
    private int liquidContainers;
    private VehicleType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(long currentFuel) {
        this.currentFuel = currentFuel;
    }

    public long getCarryingCapacity() {
        return carryingCapacity;
    }

    public void setCarryingCapacity(long carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    public long getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(long fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    public int getTotalContainers() {
        return totalContainers;
    }

    public void setTotalContainers(int totalContainers) {
        this.totalContainers = totalContainers;
    }

    public int getDryStorageContainers() {
        return dryStorageContainers;
    }

    public void setDryStorageContainers(int dryStorageContainers) {
        this.dryStorageContainers = dryStorageContainers;
    }

    public int getOpenTopContainers() {
        return openTopContainers;
    }

    public void setOpenTopContainers(int openTopContainers) {
        this.openTopContainers = openTopContainers;
    }

    public int getOpenSideContainers() {
        return openSideContainers;
    }

    public void setOpenSideContainers(int openSideContainers) {
        this.openSideContainers = openSideContainers;
    }

    public int getRefrigeratedContainers() {
        return refrigeratedContainers;
    }

    public void setRefrigeratedContainers(int refrigeratedContainers) {
        this.refrigeratedContainers = refrigeratedContainers;
    }

    public int getLiquidContainers() {
        return liquidContainers;
    }

    public void setLiquidContainers(int liquidContainers) {
        this.liquidContainers = liquidContainers;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }
}

enum VehicleType {
    SHIP,
    TRUCK,
    REEFER_TRUCK,
    TANKER_TRUCK
}
