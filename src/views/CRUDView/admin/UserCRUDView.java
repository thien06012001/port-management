package views.CRUDView.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import CRUD.PortCRUD;
import CRUD.UserCRUD;
import models.port.Port;
import models.user.PortManager;
import models.user.User;

public class UserCRUDView {
    static UserCRUD crud = new UserCRUD();
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void displayAllUsers() {
        System.out.println("\033c");
        List<User> users = crud.readAllUsers();
        for (User user : users) {
            System.out.println("Username: " + user.getUsername());
            System.out.println("Password: " + user.getPassword());
            System.out.println("Role: " + user.getRole());
            System.out.println("Associated Port: " + user.getAssociatedPort());
            System.out.println();
        }
    }

    public static void addAPortManager() {
        System.out.println("\033c");
        try {
            System.out.println("Enter An Username:");
            String username = reader.readLine();
            List<User> users = crud.readAllUsers();
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    System.out.println("\033c");
                    System.out.println("User already existed.");
                    return;
                }
            }
            System.out.println("Enter A Password:");
            String password = reader.readLine();
            System.out.println("Enter The Associated Port:");
            String associatedPort = reader.readLine();
            PortCRUD portCrud = new PortCRUD();
            Port port = portCrud.readPort(associatedPort);
            if (port == null) {

                System.out.println("\033c");
                System.out.println("There is no associated port in the system.");
                return;

            }
            crud.addPortManger(new PortManager(username, password, "PortManager", associatedPort,false));
            System.out.println("\033c");
            System.out.println("Port manager added successfully!");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void updateAPortManager() {
        System.out.println("\033c");
        try {
            System.out.println("Enter Username to update:");
            String selectedUsername = reader.readLine();
            System.out.println("Enter new Username:");
            String updatedUsername = reader.readLine();
            List<User> users = crud.readAllUsers();
            for (User user : users) {
                if (!user.getUsername().equals(updatedUsername)) {
                    System.out.println("\033c");
                    System.out.println("User not found.");
                    return;
                }
            }
            System.out.println("Enter A Password:");
            String updatePassword = reader.readLine();
            System.out.println("Enter The Associated Port:");
            String updatedAssociatedPort = reader.readLine();
            PortCRUD portCrud = new PortCRUD();
            Port port = portCrud.readPort(updatedAssociatedPort);
            if (port == null) {

                System.out.println("\033c");
                System.out.println("There is no associated port in the system.");
                return;

            }
            crud.updateUsers(selectedUsername,
                    new PortManager(updatedUsername, updatePassword, "PortManager", updatedAssociatedPort,false));
            System.out.println("\033c");
            System.out.println("User update successful!");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }
    }

    public static void deleteUser() {
        System.out.println("\033c");
        try {
            System.out.println("Enter Username to delete:");
            String deletedUsername = reader.readLine();
             List<User> users = crud.readAllUsers();
            for (User user : users) {
                if (user.getUsername().equals(deletedUsername)) {
                    System.out.println("\033c");
                    System.out.println("User already existed.");
                    return;
                }
            }
            crud.deleteUser(deletedUsername);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void displayUserCRUD() {

        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Display all users");
            System.out.println("2. Add a port manager");
            System.out.println("3. Update a Port Manager");
            System.out.println("4. Delete an user");
            System.out.println("5. Exit");
            try {
                int choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║           All User         ║");
                        System.out.println("╚════════════════════════════╝");
                        displayAllUsers();
                        break;
                    case 2:
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║         Add Manager        ║");
                        System.out.println("╚════════════════════════════╝");
                        addAPortManager();
                        break;
                    case 3:
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║        Update Manager      ║");
                        System.out.println("╚════════════════════════════╝");
                        updateAPortManager();
                        break;
                    case 4:
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║        Delete Manager      ║");
                        System.out.println("╚════════════════════════════╝");
                        deleteUser();
                        break;
                    case 5:
                        System.out.println("Going back...");
                        System.out.print("\033c");
                        return;
                    default:
                        System.out.print("\033c");
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
