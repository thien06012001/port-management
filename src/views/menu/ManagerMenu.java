package views.menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ManagerMenu extends Menu{
    public static void displayManagerMenu() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            // Display the admin-specific menu options
        System.out.println("=============Manager Menu=============");
        System.out.println("1. Port");
        System.out.println("2. Vehicle");
        System.out.println("3. Container");
        System.out.println("4. Statistics");
        System.out.println("5. Log out");

            try {
                int choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        controller.ManagerController.managePortRestricted();
                        break;
                    case 2:
                        controller.ManagerController.manageVehicleRestricted();
                        break;
                    case 3:
                        controller.AdminController.manageContainer();
                        break;
                    case 4:
                        controller.Statistics.viewStats();
                        break;
                    case 5:
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
