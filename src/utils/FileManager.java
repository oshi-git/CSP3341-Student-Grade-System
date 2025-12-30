package utils;

import java.io.*;

public class FileManager {
    
    public static boolean fileExists(String filename) {
        File file = new File(filename);
        return file.exists() && file.isFile();
    }
    
    public static boolean createFileIfNotExists(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                return file.createNewFile();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
            return false;
        }
    }
    
    public static String readFile(String filename) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return content.toString();
    }
}
