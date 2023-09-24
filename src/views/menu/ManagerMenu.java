package views.menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import CRUD.UserCRUD;
import models.user.Admin;
import models.user.PortManager;
import models.user.User;

public class ManagerMenu extends Menu {
    public static void logout() {
        UserCRUD userCRUD = new UserCRUD();
        User user = userCRUD.readAuthenticatedUser();
        if (user.getRole().equals("Admin")) {
            userCRUD.updateUsers(user.getUsername(),
                    new Admin(user.getUsername(), user.getPassword(),
                            "Admin",
                            false));
        } else {
            userCRUD.updateUsers(user.getUsername(),
                    new PortManager(user.getUsername(), user.getPassword(),
                            "PortManager", user.getAssociatedPort(),
                            false));
        }
    }

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
                        System.out.println("\033c");
                        controller.ManagerController.managePortRestricted();
                        break;
                    case 2:
                        System.out.println("\033c");
                        controller.ManagerController.manageVehicleRestricted();
                        break;
                    case 3:
                        System.out.println("\033c");
                        controller.ManagerController.manageContainerRestricted();
                        break;
                    case 4:
                        System.out.println("\033c");
                        controller.ManagerController.viewStats();
                        break;
                    case 5:
                        logout();
                        System.out.println("\033c");
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
