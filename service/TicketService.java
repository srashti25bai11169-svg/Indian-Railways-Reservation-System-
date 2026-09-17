package service;

import exception.SeatNotAvailableException;
import exception.TicketNotFoundException;
import model.Passenger;
import model.Ticket;
import model.Train;
import model.User;
import util.FileManager;
import util.PNRGenerator;

import java.util.ArrayList;
import java.util.List;

public class TicketService {
    private final String TICKET_FILE = "tickets.txt";
    private final String PASSENGER_FILE = "passengers.txt";

    private List<Ticket> tickets;
    private List<Passenger> passengers;

    public TicketService() {
        tickets = new ArrayList<>();
        passengers = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        tickets.clear();
        passengers.clear();
        
        List<String> tLines = FileManager.readFile(TICKET_FILE);
        for (String line : tLines) {
            String[] p = line.split("\\|");
            if (p.length == 7) {
                tickets.add(new Ticket(p[0], p[1], Integer.parseInt(p[2]), p[3], p[4], Double.parseDouble(p[5]), p[6]));
            }
        }

        List<String> pLines = FileManager.readFile(PASSENGER_FILE);
        for (String line : pLines) {
            String[] p = line.split("\\|");
            if (p.length == 4) {
                passengers.add(new Passenger(p[0], p[1], Integer.parseInt(p[2]), p[3]));
            }
        }
    }

    private void saveData() {
        List<String> tLines = new ArrayList<>();
        for (Ticket t : tickets) tLines.add(t.toFileString());
        FileManager.writeFile(TICKET_FILE, tLines);

        List<String> pLines = new ArrayList<>();
        for (Passenger p : passengers) pLines.add(p.toFileString());
        FileManager.writeFile(PASSENGER_FILE, pLines);
    }

    public int getAvailableSeats(Train train) {
        int booked = 0;
        for (Ticket t : tickets) {
            if (t.getTrainNumber() == train.getTrainNumber() && t.getStatus().equals("CONFIRMED")) {
                booked++;
            }
        }
        return train.getTotalSeats() - booked;
    }

    public String assignSeat(Train train) throws SeatNotAvailableException {
        if (getAvailableSeats(train) <= 0) {
            throw new SeatNotAvailableException("No seats available on this train.");
        }

        List<Integer> occupiedSeats = new ArrayList<>();
        for (Ticket t : tickets) {
            if (t.getTrainNumber() == train.getTrainNumber() && t.getStatus().equals("CONFIRMED")) {
                occupiedSeats.add(Integer.parseInt(t.getSeatNumber().substring(1)));
            }
        }

        for (int i = 1; i <= train.getTotalSeats(); i++) {
            if (!occupiedSeats.contains(i)) {
                return "S" + i;
            }
        }
        throw new SeatNotAvailableException("Seat calculation error.");
    }

    public void bookTicket(User user, Train train, String pName, int age, String gender) {
        try {
            String seatNumber = assignSeat(train);
            
            String passengerId = PNRGenerator.generatePassengerId();
            Passenger passenger = new Passenger(passengerId, pName, age, gender);
            passengers.add(passenger);

            String pnr = PNRGenerator.generatePNR();
            Ticket ticket = new Ticket(pnr, user.getUserId(), train.getTrainNumber(), passengerId, seatNumber, train.getFare(), "CONFIRMED");
            tickets.add(ticket);
            
            saveData();
            
            System.out.println("\n========================================");
            System.out.println("            TICKET CONFIRMED            ");
            System.out.println("========================================");
            System.out.println("PNR           : " + pnr);
            System.out.println("Train Number  : " + train.getTrainNumber());
            System.out.println("Train Name    : " + train.getTrainName());
            System.out.println("From          : " + train.getSource());
            System.out.println("To            : " + train.getDestination());
            System.out.println("Passenger     : " + passenger.getName());
            System.out.println("Age           : " + passenger.getAge());
            System.out.println("Gender        : " + passenger.getGender());
            System.out.println("Seat          : " + seatNumber);
            System.out.println("Fare          : Rs. " + train.getFare());
            System.out.println("Status        : CONFIRMED");
            System.out.println("========================================\n");

        } catch (SeatNotAvailableException e) {
            System.out.println(e.getMessage());
        }
    }

    public void cancelTicket(String pnr, User user) {
        try {
            Ticket ticket = getTicketByPNR(pnr);
            if (!ticket.getUserId().equals(user.getUserId()) && !user.getRole().equals("ADMIN")) {
                System.out.println("Unauthorized: You do not own this ticket.");
                return;
            }
            if (ticket.getStatus().equals("CANCELLED")) {
                System.out.println("Ticket is already cancelled.");
                return;
            }

            ticket.setStatus("CANCELLED");
            saveData();
            System.out.println("Ticket " + pnr + " cancelled successfully. Seat " + ticket.getSeatNumber() + " is now free.");
        } catch (TicketNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewTicket(String pnr) {
        try {
            Ticket t = getTicketByPNR(pnr);
            Passenger p = getPassengerById(t.getPassengerId());
            System.out.println("\n--- TICKET DETAILS ---");
            System.out.println("PNR: " + t.getPnr() + " | Status: " + t.getStatus());
            System.out.println("Train No: " + t.getTrainNumber());
            System.out.println("Passenger: " + p.getName() + " (" + p.getAge() + ", " + p.getGender() + ")");
            System.out.println("Seat: " + t.getSeatNumber());
            System.out.println("Fare: Rs. " + t.getFare());
        } catch (TicketNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public Ticket getTicketByPNR(String pnr) throws TicketNotFoundException {
        for (Ticket t : tickets) {
            if (t.getPnr().equalsIgnoreCase(pnr)) {
                return t;
            }
        }
        throw new TicketNotFoundException("Ticket with PNR " + pnr + " does not exist.");
    }

    private Passenger getPassengerById(String id) {
        for (Passenger p : passengers) {
            if (p.getPassengerId().equals(id)) return p;
        }
        return new Passenger(id, "Unknown", 0, "N/A");
    }

    public void viewBookingHistory(String userId) {
        System.out.println("\n--- YOUR BOOKINGS ---");
        boolean found = false;
        for (Ticket t : tickets) {
            if (t.getUserId().equals(userId)) {
                System.out.println(t.getPnr() + " | Train: " + t.getTrainNumber() + " | Seat: " + t.getSeatNumber() + " | Status: " + t.getStatus());
                found = true;
            }
        }
        if (!found) System.out.println("No bookings found.");
    }

    public void viewAllBookings() {
        System.out.println("\n--- ALL BOOKINGS IN SYSTEM ---");
        if (tickets.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        for (Ticket t : tickets) {
            System.out.println(t.getPnr() + " | User: " + t.getUserId() + " | Train: " + t.getTrainNumber() + " | Seat: " + t.getSeatNumber() + " | Status: " + t.getStatus());
        }
    }
}