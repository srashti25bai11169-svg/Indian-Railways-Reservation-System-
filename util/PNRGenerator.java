package util;

import java.util.List;

public class PNRGenerator {
    
    public static String generatePNR() {
        List<String> records = FileManager.readFile("tickets.txt");
        if (records.isEmpty()) {
            return "PNR1001";
        }
        
        String lastRecord = records.get(records.size() - 1);
        String lastPnr = lastRecord.split("\\|")[0];
        
        try {
            int num = Integer.parseInt(lastPnr.substring(3));
            return "PNR" + (num + 1);
        } catch (Exception e) {
            return "PNR1001";
        }
    }

    public static String generateUserId() {
        List<String> records = FileManager.readFile("users.txt");
        return "U00" + (records.size() + 1);
    }

    public static String generatePassengerId() {
        List<String> records = FileManager.readFile("passengers.txt");
        return "P00" + (records.size() + 1);
    }
}