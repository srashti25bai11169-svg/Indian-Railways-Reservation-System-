package util;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String DATA_DIR = "data";

    static {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public static List<String> readFile(String fileName) {
        List lines = new ArrayList<>();
        File file = new File(DATA_DIR + File.separator + fileName);
        
        if (!file.exists()) {
            return lines;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + fileName);
        }
        return lines;
    }

    public static void writeFile(String fileName, List<String> lines) {
        File file = new File(DATA_DIR + File.separator + fileName);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + fileName);
        }
    }
}
