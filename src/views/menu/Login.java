package views.menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import models.user.Admin;
import models.user.PortManager;
import views.welcomePageView.WelcomePageView;

public class Login {

    private static final String USER_FILE_PATH = System.getProperty("user.dir") + "/src/database/User.txt";

    private Map<String, UserCredentials> users = new HashMap<>();

    public Login() {
        loadUsers();
    }

    private void loadUsers() {
        try {
            File file = new File(USER_FILE_PATH);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Skip comment lines and empty lines
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(", ");
                if (parts.length < 3) {
                    System.err.println("Invalid line: " + line);
                    continue;
                }

                String username = parts[0];
                String password = parts[1];
                String role = parts[2];
                String associatedPort = parts.length > 3 ? parts[3] : "";

                users.put(username, new UserCredentials(username, password, role, associatedPort));

            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }

    }

    public void promptLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("╔════════════════════════════╗");
        System.out.println("║         LOGIN PAGE         ║");
        System.out.println("╚════════════════════════════╝");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        UserCredentials user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("\033c");
            // System.out.println("Logged in as " + user.getRole());
            System.out.println("Logged in successfully!");

            if (user.getAssociatedPort() != null) {
                if ("Admin".equals(user.getRole())) {
                    System.out.println("Welcome back. You can can access and process all the information as an Admin");
                    views.menu.AdminMenu.displayAdminMenu();

                } else {
                    System.out.println("Associated Port: " + user.getAssociatedPort());
                    views.menu.ManagerMenu.displayManagerMenu();
                }
            }
        } else {
            System.out.println("Invalid username or password.");
        }

    }

    private static class UserCredentials {
        private String username;
        private String password;
        private String role;
        private String associatedPort;

        public UserCredentials(String username, String password, String role, String associatedPort) {
            this.username = username;
            this.password = password;
            this.role = role;
            this.associatedPort = associatedPort;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }

        public String getAssociatedPort() {
            return associatedPort;
        }
    }

    public static boolean displayLogin() {
        Login login = new Login();
        Scanner scanner = new Scanner(System.in);
        boolean continueLogin = true; // Add a flag to control the loop

        while (continueLogin) {
            login.promptLogin();
            System.out.print("Enter 'exit' to quit or any other key to continue: ");
            String decision = scanner.nextLine().trim();
            if ("exit".equalsIgnoreCase(decision)) {
                WelcomePageView welcomePageView = new WelcomePageView();
                welcomePageView.displayWelcomePage(); // Return to the welcome page
                continueLogin = false; // Set the flag to exit the loop

            }
            System.out.println("\033c");
        }
        return continueLogin;
    }

    // Testing
    public void displayAllUsers() {
        System.out.println("\nDisplaying All Users from USER.TXT:");
        System.out.println("-----------------------------------------------------------");
        System.out.printf("%-15s %-15s %-15s %-15s%n", "Username", "Password", "Role", "Associated Port");
        System.out.println("-----------------------------------------------------------");
        for (UserCredentials user : users.values()) {
            System.out.printf("%-15s %-15s %-15s %-15s%n", user.username, user.password, user.role,
                    user.associatedPort);
        }
        System.out.println("-----------------------------------------------------------");
    }
}
