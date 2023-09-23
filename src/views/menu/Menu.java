package views.menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.*;
import models.trip.Trip;
import models.user.User;
import views.menu.Login;
public class Menu {

    private void welcome() {
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("CONTAINER PORT MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Minh Vu & Dr. Phong Ngo");
        System.out.println("Group: Team 8");
        System.out.println("Chau Chan Bang");
        System.out.println("Chau Chan Thien");
        System.out.println("Trinh Nguyen Ha");
        System.out.println("Nguyen Minh Khai");
        printSectionDelimeter();
    }

    private void printSectionDelimeter() {
        System.out.println("------------------------------------------------------------");
    }

    private void afterLogin() {
    try {
        
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    }

    public void display() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        welcome();
        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            try {
                int choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        Login.displayLogin();
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

    public double getFuelConsumptionADay(Date date) {
        return 0; // Đợi TripCRUD
    }

    
}
    

