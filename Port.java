import java.util.List;

public class Port {
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private long storingCapacity;
    private boolean landingAbility;
    private long totalWeightContainers;
    private int numberContainers;
    private int numberVehicles;
    private List<Trip> trafficHistory;
    private User manager;

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getStoringCapacity() {
        return storingCapacity;
    }

    public void setStoringCapacity(long storingCapacity) {
        this.storingCapacity = storingCapacity;
    }

    public boolean hasLandingAbility() {
        return landingAbility;
    }

    public void setLandingAbility(boolean landingAbility) {
        this.landingAbility = landingAbility;
    }

    public long getTotalWeightContainers() {
        return totalWeightContainers;
    }

    public void setTotalWeightContainers(long totalWeightContainers) {
        this.totalWeightContainers = totalWeightContainers;
    }

    public int getNumberContainers() {
        return numberContainers;
    }

    public void setNumberContainers(int numberContainers) {
        this.numberContainers = numberContainers;
    }

    public int getNumberVehicles() {
        return numberVehicles;
    }

    public void setNumberVehicles(int numberVehicles) {
        this.numberVehicles = numberVehicles;
    }

    public List<Trip> getTrafficHistory() {
        return trafficHistory;
    }

    public void setTrafficHistory(List<Trip> trafficHistory) {
        this.trafficHistory = trafficHistory;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

}
