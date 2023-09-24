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
                        System.out.println("\033c");
                        controller.AdminController.managePort();
                        break;
                    case 2:
                        System.out.println("\033c");
                        controller.AdminController.manageVehicle();
                        break;
                    case 3:
                        System.out.println("\033c");
                        controller.AdminController.manageContainer();
                        break;
                    case 4:
                        System.out.println("\033c");
                        controller.AdminController.manageUser();
                        break;
                    case 5:
                        System.out.println("\033c");
                        controller.AdminController.manageTrip();
                        break;
                    case 6:
                        System.out.println("\033c");
                        controller.Statistics.viewStats();
                        break;
                    case 7:
                        System.out.println("\033c");
                        return;
                    default:
                        System.out.println("\033c");
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
