package models.user;

import models.port.Port;

public class Admin extends User {
    public Admin(String username, String password, String role, Boolean isAuthenticated) {
        super(username, password, role, null, isAuthenticated);
    }
}
