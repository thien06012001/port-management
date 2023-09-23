package controller;
import views.CRUDView.*;

public class ManagerController {

    public static void managePortRestricted() {
        PortCRUDViewMana portCrudViewMana = new PortCRUDViewMana();
        portCrudViewMana.displayPortCRUD();
    }

    public static void manageVehicleRestricted() {
    System.out.println("============= Vehicles =============");
    System.out.println("1. View all vehicles");
    System.out.println("2. Add a new vehicle");
    System.out.println("3. Remove a vehicle");
    System.out.println("4. Update vehicle information");
    System.out.println("5. Load/unload a container to/from a vehicle");
    System.out.println("6. Check if a vehicle can move to another port");
    System.out.println("7. Move a vehicle to another port");
    System.out.println("8. Refuel a vehicle");
    System.out.println("9. Back"); // Add an option to exit the vehicle menu


    }
}