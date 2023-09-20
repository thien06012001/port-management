//package portManagement;
//
//import java.util.Date;
//
//public class Main {
//    public static void main(String[] args) {
//        // Create some ports
//        Port portA = new Port("p-001", "Port A", 40.7128, 74.0060, 1000, true);
//        Port portB = new Port("p-002", "Port B", 34.0522, 118.2437, 1200, true);
//
//        // Create some vehicles
//        Vehicle ship1 = new Vehicle("sh-001", "Ship One", "Ship", 500, 10000);
//        ship1.setCurrentPort(portA);
//        Vehicle truck1 = new Vehicle("tr-001", "Truck One", "Truck", 100, 2000);
//        truck1.setCurrentPort(portB);
//
//
//        // Create some containers
//        Container container1 = new Container("c-001", 500, "DryStorage");
//        Container container2 = new Container("c-002", 600, "OpenTop");
//
//        // Load containers onto vehicles
//        ship1.loadContainer(container1);
//        truck1.loadContainer(container2);
//
//        // Move vehicles to ports
//        ship1.moveToPort(portB);
//        truck1.moveToPort(portA);
//
//        // Create a trip
//        Trip trip1 = new Trip(ship1, new Date(), portA, portB);
//
//        // Update trip status
//        trip1.updateStatus("In Transit");
//
//        // Create some users
//        User admin = new User("admin", "admin123", "Admin");
//        User portManagerA = new User("managerA", "managerA123", "PortManager");
//        portManagerA.setAssociatedPort(portA);
//
//        // Authenticate users
//        boolean isAdminAuthenticated = admin.authenticate("admin123");
//        boolean isManagerAAuthenticated = portManagerA.authenticate("managerA123");
//
//        // Print out results
//        System.out.println("Is Admin authenticated? " + isAdminAuthenticated);
//        System.out.println("Is Port Manager A authenticated? " + isManagerAAuthenticated);
//        System.out.println("Ship One's current port: " + ship1.getCurrentPort().getName());
//        System.out.println("Truck One's current port: " + truck1.getCurrentPort().getName());
//        System.out.println("Trip 1 status: " + trip1.getStatus());
//
//
//        double result = portA.calculateDistanceTo(portB);
//        System.out.println(result);
//    }
//}
