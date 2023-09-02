import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class User {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a list of users
        List<Authentication> users = new ArrayList<>();
        users.add(new SystemAdmin("admin", "adminPassword"));
        users.add(new PortManager("portManager", "managerPassword"));

        // Example login process
        Authentication currentUser = login(scanner, users);

        if (currentUser != null) {
            // Display the menu based on user role
            displayMenu(currentUser);
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    // Simplified login method
    public static Authentication login(Scanner scanner, List<Authentication> users) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (Authentication user : users) {
            if (user.authenticate(username, password)) {
                return user;
            }
        }

        return null; // Login failed
    }

    // Simplified menu display based on user role
    public static void displayMenu(Authentication user) {
        System.out.println("Welcome, " + user.getUsername() + "!");
        System.out.println("Menu Options:");

        if (user instanceof SystemAdmin) {
            // System admin menu
            System.out.println("1. View All Ports");
            System.out.println("2. Manage Users");
        } else if (user instanceof PortManager) {
            // Port manager menu
            System.out.println("1. View Port Information");
            System.out.println("2. Process Containers");
        }

        System.out.println("0. Logout");
    }
}

abstract class Authentication {
    private String username;
    private String password;

    public Authentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    // Authenticate user based on username and password
    public boolean authenticate(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }
}

class SystemAdmin extends Authentication {
    public SystemAdmin(String username, String password) {
        super(username, password);
    }
    // Additional methods and permissions for the system admin
}

class PortManager extends Authentication {

    public PortManager(String username, String password) {
        super(username, password);

    }

    // Additional methods and permissions for the port manager
}
