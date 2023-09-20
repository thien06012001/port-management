
public interface IPort {
    double calculateDistanceTo(Port otherPort);

    void addVehicle(Vehicle vehicle);

    void removeVehicle(Vehicle vehicle);

    void addContainer(Container container);

    void removeContainer(Container container);

    double getTotalWeight();
}
