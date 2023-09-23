package controller;

import views.CRUDView.PortCRUDView;

public class AdminController {
    public static void managePort() {
    PortCRUDView portCRUDView = new PortCRUDView();   
    portCRUDView.displayPortCRUD();
}

    public static void manageContainer() {
    System.out.println("============= Containers =============");
    System.out.println("1. View all containers");
    System.out.println("2. Add a new container");
    System.out.println("3. Remove a container");
    System.out.println("4. Update container information");
    System.out.println("5. List all containers in a port");
    System.out.println("6. Check if a container can be loaded onto a vehicle");
    System.out.println("7. Load/unload a container to/from a vehicle");
    System.out.println("8. Exit"); // Add an option to exit the container management menu

    }

    public static void manageVehicle() {
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

    public static void manageTrip() {
    System.out.println("============= Trips =============");
    System.out.println("1. View all trips");
    System.out.println("2. Create a new trip");
    System.out.println("3. Update trip information");
    System.out.println("4. List all trips in a given day");
    System.out.println("5. List all trips from day A to day B");
    System.out.println("6. Back"); // Add an option to exit the trip management menu
    }

    public static void manageUser() {
    System.out.println("============= Human Resources =============");
    System.out.println("1. View all port managers");
    System.out.println("2. Add a new manager");
    System.out.println("3. Remove a manager");
    System.out.println("4. Update manager information");
    System.out.println("5. Back"); // Add an option to exit the user management menu

    }

}