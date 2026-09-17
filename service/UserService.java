package service;

import model.User;
import util.FileManager;
import util.PNRGenerator;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final String FILE_NAME = "users.txt";
    private List<User> users;

    public UserService() {
        users = new ArrayList<>();
        loadUsers();
        createDefaultAdmin();
    }

    private void loadUsers() {
        List<String> lines = FileManager.readFile(FILE_NAME);
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 5) {
                users.add(new User(parts[0], parts[1], parts[2], parts[3], parts[4]));
            }
        }
    }

    private void saveUsers() {
        List<String> lines = new ArrayList<>();
        for (User u : users) {
            lines.add(u.toFileString());
        }
        FileManager.writeFile(FILE_NAME, lines);
    }

    private void createDefaultAdmin() {
        boolean adminExists = false;
        for (User u : users) {
            if (u.getRole().equals("ADMIN")) {
                adminExists = true;
                break;
            }
        }
        if (!adminExists) {
            User admin = new User("A001", "Administrator", "admin", "admin123", "ADMIN");
            users.add(admin);
            saveUsers();
        }
    }

    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public boolean registerUser(String name, String username, String password) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Username already exists!");
                return false;
            }
        }
        
        String userId = PNRGenerator.generateUserId();
        User newUser = new User(userId, name, username, password, "USER");
        users.add(newUser);
        saveUsers();
        return true;
    }
    
    public User getUserById(String userId) {
        for (User u : users) {
            if (u.getUserId().equals(userId)) return u;
        }
        return null;
    }
}