package CRUD;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.user.Admin;
import models.user.PortManager;
import models.user.User;
import views.menu.Login;

public class UserCRUD {

    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/User.txt";

    public List<User> readAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            File file = new File(FILE_PATH); // Replace with your file path
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Skip comment lines and empty lines
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(", ");
                if (parts.length != 4) {
                    System.err.println("Invalid line: " + line);
                    continue;
                }

                String username = parts[0];
                String password = parts[1];
                String role = parts[2];
                String associatedPort = parts[3];
                User user = new PortManager(username, password, role, associatedPort);
                users.add(user);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        return users;
    }

    public void addPortManger(PortManager portManager) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write("\n" + portManager.getUsername() + ", " + portManager.getPassword() + ", " + portManager.getRole() +", "
                    + portManager.getAssociatedPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAdmin(Admin admin) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write("\n" + admin.getUsername() + ", " + admin.getPassword() + ", " + admin.getRole() +", "
                    + admin.getAssociatedPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUsers(String username, User updatedUser) {
        List<User> users = readAllUsers();
        int index = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            users.set(index, updatedUser);
            writeAllUsers(users);
        }
    }

    public void deleteUser(String username) {
        List<User> users = readAllUsers();
        users.removeIf(user -> user.getUsername().equals(username));
        writeAllUsers(users);
    }

    private void writeAllUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("# Container File\n# Format: Username, Password, Role, associatedPort");
            for (User user : users) {
                bw.write("\n" + user.getUsername() + ", " + user.getPassword() + ", " + user.getRole()+", "
                        + user.getAssociatedPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UserCRUD crud = new UserCRUD();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Login login = new Login();
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
                        views.menu.Login.displayLogin(); // Call the displayLogin method from the Login class
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
