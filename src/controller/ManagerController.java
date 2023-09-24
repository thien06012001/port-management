package controller;
import views.CRUDView.manager.*;


public class ManagerController {

    public static void managePortRestricted() {
        PortCRUDViewMana portCrudViewMana = new PortCRUDViewMana();
        portCrudViewMana.displayPortCRUD();
    }

    public static void manageVehicleRestricted() {
        VehicleCRUDViewMana vehicleCRUDViewMana = new VehicleCRUDViewMana();
        vehicleCRUDViewMana.displayVehicleCRUDMana();
    }
    public static void manageContainerRestricted() {
        ContainerCRUDViewMana containerCRUDViewMana = new ContainerCRUDViewMana();
        containerCRUDViewMana.displayContainerCRUDMana();
    }
    public static void viewStats() {
        TripCRUDViewMana tripCRUDViewMana = new TripCRUDViewMana();
        tripCRUDViewMana.displayTripCRUDView();
    }
}