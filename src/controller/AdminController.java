package controller;

import views.CRUDView.*;


public class AdminController {
    public static void managePort() {
    PortCRUDView portCRUDView = new PortCRUDView();   
    portCRUDView.displayPortCRUD();
}

    public static void manageContainer() {
    ContainerCRUDView containerCRUDView = new ContainerCRUDView();
    containerCRUDView.displayContainerCRUD();


    }

    public static void manageVehicle() {
        VehicleCRUDView vehicleCRUDView = new VehicleCRUDView();
        vehicleCRUDView.displayVehicleCRUD();
    
    }

    public static void manageTrip() {
        TripCRUDView tripCrudView = new TripCRUDView();
        tripCrudView.displayTripCRUDView();
    }

    public static void manageUser() {
        UserCRUDView userCRUDView = new UserCRUDView();
        userCRUDView.displayUserCRUD();

    }

}