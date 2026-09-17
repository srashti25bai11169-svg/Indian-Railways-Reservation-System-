package model;

public class Ticket {
    private String pnr;
    private String userId;
    private int trainNumber;
    private String passengerId;
    private String seatNumber;
    private double fare;
    private String status; // CONFIRMED or CANCELLED

    public Ticket(String pnr, String userId, int trainNumber, String passengerId, String seatNumber, double fare, String status) {
        this.pnr = pnr;
        this.userId = userId;
        this.trainNumber = trainNumber;
        this.passengerId = passengerId;
        this.seatNumber = seatNumber;
        this.fare = fare;
        this.status = status;
    }

    public String getPnr() { return pnr; }
    public String getUserId() { return userId; }
    public int getTrainNumber() { return trainNumber; }
    public String getPassengerId() { return passengerId; }
    public String getSeatNumber() { return seatNumber; }
    public double getFare() { return fare; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String toFileString() {
        return pnr + "|" + userId + "|" + trainNumber + "|" + passengerId + "|" + seatNumber + "|" + fare + "|" + status;
    }
}