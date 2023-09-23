package models.user;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.container.Container;
import models.port.Port;
import models.trip.Trip;
import models.vehicle.Vehicle;

public abstract class User {
    private String username;
    private String password;
    private String role; // e.g. "Admin", "PortManager"
    private String associatedPort; // for Port Managers

    // Constructor
  
  

    public String getPassword() {
		return password;
	}
	public User(String username, String password, String role, String associatedPort) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.associatedPort = associatedPort;
    }
 

     

    // Authenticate user based on entered password
    public boolean authenticate(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }

    // Getters and setters for relevant attributes

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getAssociatedPort() {
        return associatedPort;
    }

    public void setAssociatedPort(String associatedPort) {
        if ("PortManager".equals(this.role)) {
            this.associatedPort = associatedPort;
        } else {
            System.out.println("Only Port Managers can have an associated port.");
        }
    }
}
