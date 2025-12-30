package services;

import models.Student;
import java.util.*;
import java.io.*;

public class StudentService {
    private Map<String, Student> students;
    private static final String DATA_FILE = "students.txt";
    
    public StudentService() {
        this.students = new HashMap<>();
    }
    
    public boolean addStudent(Student student) {
        if (students.containsKey(student.getId())) {
            return false;
        }
        students.put(student.getId(), student);
        return true;
    }
    
    public Student getStudent(String id) {
        return students.get(id);
    }
    
    public Collection<Student> getAllStudents() {
        return students.values();
    }
    
    public boolean removeStudent(String id) {
        return students.remove(id) != null;
    }
    
    public int getStudentCount() {
        return students.size();
    }
    
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Student student : students.values()) {
                writer.println(student.getId() + "|" + 
                             student.getName() + "|" + 
                             student.getEmail());
            }
        } catch (IOException e) {
            System.err.println("Error saving student data: " + e.getMessage());
        }
    }
    
    public void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return;
        }
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    Student student = new Student(parts[0], parts[1], parts[2]);
                    students.put(student.getId(), student);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error loading student data: " + e.getMessage());
        }
    }
}
