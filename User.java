
public class User {
    private String username;
    private String password;
    private String role; // e.g. "Admin", "PortManager"
    private Port associatedPort; // for Port Managers

    // Constructor
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
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

    public Port getAssociatedPort() {
        return associatedPort;
    }

    public void setAssociatedPort(Port associatedPort) {
        if ("PortManager".equals(this.role)) {
            this.associatedPort = associatedPort;
        } else {
            System.out.println("Only Port Managers can have an associated port.");
        }
    }
}
