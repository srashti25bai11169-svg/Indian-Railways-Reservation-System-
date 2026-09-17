package service;

import exception.TrainNotFoundException;
import model.Train;
import model.User;
import util.InputHelper;

public class ReservationSystem {
    private UserService userService;
    private TrainService trainService;
    private TicketService ticketService;
    private User loggedInUser;

    public ReservationSystem() {
        userService = new UserService();
        trainService = new TrainService();
        ticketService = new TicketService();
    }

    public void start() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("        RAIL RESERVATION SYSTEM         ");
            System.out.println("========================================");
            System.out.println("1. User Login");
            System.out.println("2. User Registration");
            System.out.println("3. Admin Login");
            System.out.println("4. View All Trains");
            System.out.println("5. Search Train");
            System.out.println("6. Exit");
            
            int choice = InputHelper.getInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    loginFlow("USER");
                    break;
                case 2:
                    registerFlow();
                    break;
                case 3:
                    loginFlow("ADMIN");
                    break;
                case 4:
                    trainService.displayAllTrains();
                    break;
                case 5:
                    searchTrainFlow();
                    break;
                case 6:
                    System.out.println("Thank you for using Rail Reservation System!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void loginFlow(String expectedRole) {
        String user = InputHelper.getString("Username: ");
        String pass = InputHelper.getString("Password: ");
        
        loggedInUser = userService.login(user, pass);
        
        if (loggedInUser != null) {
            if (!loggedInUser.getRole().equals(expectedRole)) {
                System.out.println("Access Denied. Incorrect Role.");
                loggedInUser = null;
            } else {
                System.out.println("Login successful! Welcome, " + loggedInUser.getName());
                if (expectedRole.equals("USER")) userMenu();
                else adminMenu();
            }
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void registerFlow() {
        System.out.println("\n--- User Registration ---");
        String name = InputHelper.getString("Full Name: ");
        String username = InputHelper.getString("Username: ");
        String password = InputHelper.getString("Password: ");
        
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            System.out.println("Fields cannot be empty.");
            return;
        }
        
        if (userService.registerUser(name, username, password)) {
            System.out.println("Registration successful! You can now login.");
        }
    }

    private void searchTrainFlow() {
        String src = InputHelper.getString("Enter Source: ");
        String dest = InputHelper.getString("Enter Destination: ");
        trainService.searchTrains(src, dest);
    }

    private void userMenu() {
        while (loggedInUser != null) {
            System.out.println("\n========================================");
            System.out.println("               USER MENU                ");
            System.out.println("========================================");
            System.out.println("1. Search Train");
            System.out.println("2. View All Trains");
            System.out.println("3. Check Seat Availability");
            System.out.println("4. Book Ticket");
            System.out.println("5. View Ticket");
            System.out.println("6. Booking History");
            System.out.println("7. Cancel Ticket");
            System.out.println("8. Logout");
            
            int choice = InputHelper.getInt("Enter your choice: ");
            
            switch (choice) {
                case 1: searchTrainFlow(); break;
                case 2: trainService.displayAllTrains(); break;
                case 3: checkAvailabilityFlow(); break;
                case 4: bookTicketFlow(); break;
                case 5: viewTicketFlow(); break;
                case 6: ticketService.viewBookingHistory(loggedInUser.getUserId()); break;
                case 7: cancelTicketFlow(); break;
                case 8: loggedInUser = null; System.out.println("Logged out."); break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void adminMenu() {
        while (loggedInUser != null) {
            System.out.println("\n========================================");
            System.out.println("               ADMIN MENU               ");
            System.out.println("========================================");
            System.out.println("1. Add Train");
            System.out.println("2. View All Trains");
            System.out.println("3. Update Train");
            System.out.println("4. Delete Train");
            System.out.println("5. View All Bookings");
            System.out.println("6. Logout");
            
            int choice = InputHelper.getInt("Enter your choice: ");
            
            switch (choice) {
                case 1: addTrainFlow(); break;
                case 2: trainService.displayAllTrains(); break;
                case 3: updateTrainFlow(); break;
                case 4: deleteTrainFlow(); break;
                case 5: ticketService.viewAllBookings(); break;
                case 6: loggedInUser = null; System.out.println("Admin logged out."); break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void checkAvailabilityFlow() {
        int tNo = InputHelper.getInt("Enter Train Number: ");
        try {
            Train t = trainService.getTrain(tNo);
            int available = ticketService.getAvailableSeats(t);
            System.out.println("Available Seats on " + t.getTrainName() + ": " + available + "/" + t.getTotalSeats());
        } catch (TrainNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void bookTicketFlow() {
        int tNo = InputHelper.getInt("Enter Train Number to Book: ");
        try {
            Train t = trainService.getTrain(tNo);
            
            if (ticketService.getAvailableSeats(t) <= 0) {
                System.out.println("Sorry, no seats available on this train.");
                return;
            }

            System.out.println("--- Passenger Details ---");
            String pName = InputHelper.getString("Name: ");
            int age = InputHelper.getInt("Age: ");
            String gender = InputHelper.getString("Gender (Male/Female/Other): ");
            
            if (pName.isEmpty() || age <= 0) {
                System.out.println("Invalid passenger details.");
                return;
            }
            
            ticketService.bookTicket(loggedInUser, t, pName, age, gender);

        } catch (TrainNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewTicketFlow() {
        String pnr = InputHelper.getString("Enter PNR: ");
        ticketService.viewTicket(pnr);
    }

    private void cancelTicketFlow() {
        String pnr = InputHelper.getString("Enter PNR to cancel: ");
        ticketService.cancelTicket(pnr, loggedInUser);
    }

    private void addTrainFlow() {
        System.out.println("\n--- Add New Train ---");
        int num = InputHelper.getInt("Train Number: ");
        String name = InputHelper.getString("Train Name: ");
        String src = InputHelper.getString("Source: ");
        String dest = InputHelper.getString("Destination: ");
        int seats = InputHelper.getInt("Total Seats: ");
        double fare = InputHelper.getDouble("Fare: ");
        
        if (name.isEmpty() || src.isEmpty() || dest.isEmpty() || src.equalsIgnoreCase(dest) || seats <= 0 || fare < 0) {
            System.out.println("Invalid train data provided.");
            return;
        }
        
        Train t = new Train(num, name, src, dest, seats, fare);
        trainService.addTrain(t);
    }

    private void updateTrainFlow() {
        int num = InputHelper.getInt("Enter Train Number to update: ");
        String name = InputHelper.getString("New Train Name: ");
        String src = InputHelper.getString("New Source: ");
        String dest = InputHelper.getString("New Destination: ");
        int seats = InputHelper.getInt("New Total Seats: ");
        double fare = InputHelper.getDouble("New Fare: ");
        
        trainService.updateTrain(num, name, src, dest, seats, fare);
    }

    private void deleteTrainFlow() {
        int num = InputHelper.getInt("Enter Train Number to delete: ");
        trainService.deleteTrain(num);
    }
}