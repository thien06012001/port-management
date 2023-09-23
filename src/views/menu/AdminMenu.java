package views.menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class AdminMenu extends Menu {

    public static void displayAdminMenu() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            // Display the admin-specific menu options
        System.out.println("=============Admin Menu=============");
        System.out.println("1. Port");
        System.out.println("2. Vehicle");
        System.out.println("3. Container");
        System.out.println("4. Manager");
        System.out.println("5. Trip");
        System.out.println("6. Statistics");
        System.out.println("7. Log out");
            try {
                int choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        controller.AdminController.managePort();
                        break;
                    case 2:
                        controller.AdminController.manageVehicle();
                        break;
                    case 3:
                        controller.AdminController.manageContainer();
                        break;
                    case 4:
                        controller.AdminController.manageUser();
                        break;
                    case 5:
                        controller.AdminController.manageTrip();
                        break;
                    case 6:
                        controller.Statistics.viewStats();
                        break;
                    case 7:
                        views.menu.Login.displayLogin(); // Call the displayLogin method from the Login class
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    

}
