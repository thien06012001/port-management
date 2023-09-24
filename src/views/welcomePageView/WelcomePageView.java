package views.welcomePageView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import views.menu.Login;

public class WelcomePageView {

    public void displayWelcomePage() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("CONTAINER PORT MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Minh Vu & Dr. Phong Ngo");
        System.out.println("Group: Team 8");
        System.out.println("Chau Chan Bang");
        System.out.println("Chau Chan Thien");
        System.out.println("Trinh Nguyen Ha");
        System.out.println("Nguyen Minh Khai");
        System.out.println("------------------------------------------------------------");
        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            try {
                int choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        System.out.print("\033c");
                        // Login.displayLogin();
                        if (!Login.displayLogin()) {
                            return; // Exit the program if displayLogin returns false
                        }
                        break;
                    case 2:
                        System.out.println("Exiting...");
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
