package models.user;

import models.port.Port;

public class PortManager extends User {
    public PortManager(String username, String password, String role, String associatedPortId) {
        super(username, password, role, associatedPortId);
    }

}
