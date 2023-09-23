package views.CRUDView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import CRUD.UserCRUD;
import models.user.PortManager;
import models.user.User;

public class UserCRUDView {
    public void displayUserCRUD() {
        UserCRUD crud = new UserCRUD();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
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
                        List<User> users = crud.readAllUsers();
                        for (User user : users) {
                            System.out.println("Username: " + user.getUsername());
                            System.out.println("Password: " + user.getPassword());
                            System.out.println("Role: " + user.getRole());
                            System.out.println("Associated Port: " + user.getAssociatedPort());
                            System.out.println();
                        }
                        break;
                    case 2:
                        System.out.println("Enter An Username:");
                        String username = reader.readLine();
                        System.out.println("Enter A Password:");
                        String password = reader.readLine();
                        System.out.println("Enter The Associated Port:");
                        String associatedPort = reader.readLine();
                        crud.addPortManger(new PortManager(username, password, "PortManager", associatedPort));
                        break;
                    case 3:
                        System.out.println("Enter Username to update:");
                        String selectedUsername = reader.readLine();
                        System.out.println("Enter new Username:");
                        String updatedUsername = reader.readLine();
                        System.out.println("Enter new Password:");
                        String updatePassword = reader.readLine();
                        System.out.println("Enter new Role:");
                        String updatedRole = reader.readLine();
                        System.out.println("Enter new Associated Port:");
                        String updatedAssociatedPort = reader.readLine();
                        crud.updateUsers(selectedUsername,
                                new PortManager(updatedUsername, updatePassword, updatedRole, updatedAssociatedPort));
                        break;
                    case 4:
                        System.out.println("Enter Username to delete:");
                        String deletedUsername = reader.readLine();
                        crud.deleteUser(deletedUsername);
                        break;
                    case 5:
                        System.out.println("Going back...");
                        System.out.print("\033c");
                        views.menu.AdminMenu.displayAdminMenu(); // Call the displayLogin method from the Login class
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
