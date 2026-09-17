package model;

public class User extends Person {
    private String userId;
    private String username;
    private String password;
    private String role; // "USER" or "ADMIN"

    public User(String userId, String name, String username, String password, String role) {
        super(name);
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    @Override
    public String getRoleDetails() {
        return "User Role: " + role;
    }

    // Format for saving to users.txt
    public String toFileString() {
        return userId + "|" + name + "|" + username + "|" + password + "|" + role;
    }
}