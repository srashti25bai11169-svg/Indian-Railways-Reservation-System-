package model;

public class Train implements Comparable<Train> {
    private int trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private int totalSeats;
    private double fare;

    public Train(int trainNumber, String trainName, String source, String destination, int totalSeats, double fare) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.fare = fare;
    }

    public int getTrainNumber() { return trainNumber; }
    public void setTrainNumber(int trainNumber) { this.trainNumber = trainNumber; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }

    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }

    public String toFileString() {
        return trainNumber + "|" + trainName + "|" + source + "|" + destination + "|" + totalSeats + "|" + fare;
    }

    @Override
    public int compareTo(Train other) {
        return Integer.compare(this.trainNumber, other.trainNumber);
    }
}