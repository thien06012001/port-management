package interfaces;

import java.util.ArrayList;

import models.container.Container;
import models.port.Port;

public interface IVehicle {
    void loadContainer(Container container);
    void unloadContainer(Container container);
    void moveToPort(Port destinationPort);
    void refuel(double amount);
    double getTotalWeight();
    void setCurrentPort(Port port);
    Port getCurrentPort();
    double getCarryingCapacity();
    ArrayList<Container> getContainersList();

}
