package services;

import models.Grade;
import models.Course;
import java.util.*;
import java.io.*;

public class GradeService {
    private List<Grade> grades;
    private Map<String, Course> courses;
    private static final String GRADES_FILE = "grades.txt";
    private static final String COURSES_FILE = "courses.txt";
    
    public GradeService() {
        this.grades = new ArrayList<>();
        this.courses = new HashMap<>();
    }
    
    public void recordGrade(Grade grade) {
        grades.removeIf(g -> g.getStudentId().equals(grade.getStudentId()) &&
                            g.getCourseCode().equals(grade.getCourseCode()));
        grades.add(grade);
    }
    
    public List<Grade> getStudentGrades(String studentId) {
        List<Grade> studentGrades = new ArrayList<>();
        for (Grade grade : grades) {
            if (grade.getStudentId().equals(studentId)) {
                studentGrades.add(grade);
            }
        }
        return studentGrades;
    }
    
    public double calculateGPA(String studentId) {
        List<Grade> studentGrades = getStudentGrades(studentId);
        
        if (studentGrades.isEmpty()) {
            return 0.0;
        }
        
        double totalPoints = 0.0;
        for (Grade grade : studentGrades) {
            totalPoints += grade.getGradePoints();
        }
        
        return totalPoints / studentGrades.size();
    }
    
    public void addCourse(Course course) {
        courses.put(course.getCode(), course);
    }
    
    public Course getCourse(String code) {
        return courses.get(code);
    }
    
    public Collection<Course> getAllCourses() {
        return courses.values();
    }
    
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(GRADES_FILE))) {
            for (Grade grade : grades) {
                writer.println(grade.getStudentId() + "|" +
                             grade.getCourseCode() + "|" +
                             grade.getScore());
            }
        } catch (IOException e) {
            System.err.println("Error saving grades: " + e.getMessage());
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(COURSES_FILE))) {
            for (Course course : courses.values()) {
                writer.println(course.getCode() + "|" +
                             course.getName() + "|" +
                             course.getCreditHours());
            }
        } catch (IOException e) {
            System.err.println("Error saving courses: " + e.getMessage());
        }
    }
    
    public void loadFromFile() {
        File coursesFile = new File(COURSES_FILE);
        if (coursesFile.exists()) {
            try (Scanner scanner = new Scanner(coursesFile)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split("\\|");
                    if (parts.length >= 3) {
                        Course course = new Course(parts[0], parts[1], 
                                                  Integer.parseInt(parts[2]));
                        courses.put(course.getCode(), course);
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("Error loading courses: " + e.getMessage());
            }
        }
        
        File gradesFile = new File(GRADES_FILE);
        if (gradesFile.exists()) {
            try (Scanner scanner = new Scanner(gradesFile)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split("\\|");
                    if (parts.length >= 3) {
                        Grade grade = new Grade(parts[0], parts[1],
                                              Double.parseDouble(parts[2]));
                        grades.add(grade);
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("Error loading grades: " + e.getMessage());
            }
        }
    }
}
