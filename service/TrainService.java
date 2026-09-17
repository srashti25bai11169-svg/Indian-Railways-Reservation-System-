package service;

import exception.TrainNotFoundException;
import model.Train;
import util.FileManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainService {
    private final String FILE_NAME = "trains.txt";
    private List<Train> trains;

    public TrainService() {
        trains = new ArrayList<>();
        loadTrains();
    }

    private void loadTrains() {
        trains.clear();
        List<String> lines = FileManager.readFile(FILE_NAME);
        for (String line : lines) {
            String[] p = line.split("\\|");
            if (p.length == 6) {
                trains.add(new Train(Integer.parseInt(p[0]), p[1], p[2], p[3], Integer.parseInt(p[4]), Double.parseDouble(p[5])));
            }
        }
    }

    private void saveTrains() {
        List<String> lines = new ArrayList<>();
        Collections.sort(trains); 
        for (Train t : trains) {
            lines.add(t.toFileString());
        }
        FileManager.writeFile(FILE_NAME, lines);
    }

    public void addTrain(Train train) {
        for (Train t : trains) {
            if (t.getTrainNumber() == train.getTrainNumber()) {
                System.out.println("Error: Train Number already exists!");
                return;
            }
        }
        trains.add(train);
        saveTrains();
        System.out.println("Train Added Successfully!");
    }

    public Train getTrain(int trainNumber) throws TrainNotFoundException {
        for (Train t : trains) {
            if (t.getTrainNumber() == trainNumber) {
                return t;
            }
        }
        throw new TrainNotFoundException("Train with number " + trainNumber + " not found.");
    }

    public void displayAllTrains() {
        if (trains.isEmpty()) {
            System.out.println("No trains available.");
            return;
        }
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("%-10s %-20s %-15s %-15s %-10s %-10s\n", "Train No", "Train Name", "Source", "Destination", "Total Seats", "Fare");
        System.out.println("----------------------------------------------------------------------------------");
        for (Train t : trains) {
            System.out.printf("%-10d %-20s %-15s %-15s %-10d %-10.2f\n", 
                t.getTrainNumber(), t.getTrainName(), t.getSource(), t.getDestination(), t.getTotalSeats(), t.getFare());
        }
        System.out.println("----------------------------------------------------------------------------------");
    }

    public void searchTrains(String source, String destination) {
        boolean found = false;
        System.out.println("\nMatching Trains:");
        for (Train t : trains) {
            if (t.getSource().equalsIgnoreCase(source) && t.getDestination().equalsIgnoreCase(destination)) {
                System.out.printf("%d | %s | %s to %s | Fare: Rs.%.2f\n", 
                    t.getTrainNumber(), t.getTrainName(), t.getSource(), t.getDestination(), t.getFare());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No trains found for this route.");
        }
    }

    public void updateTrain(int trainNumber, String name, String src, String dest, int seats, double fare) {
        try {
            Train t = getTrain(trainNumber);
            t.setTrainName(name);
            t.setSource(src);
            t.setDestination(dest);
            t.setTotalSeats(seats);
            t.setFare(fare);
            saveTrains();
            System.out.println("Train Updated Successfully!");
        } catch (TrainNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTrain(int trainNumber) {
        try {
            Train t = getTrain(trainNumber);
            trains.remove(t);
            saveTrains();
            System.out.println("Train Deleted Successfully!");
        } catch (TrainNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}