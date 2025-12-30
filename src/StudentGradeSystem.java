import models.*;
import services.*;
import java.util.Scanner;

public class StudentGradeSystem {
    
    private static StudentService studentService = new StudentService();
    private static GradeService gradeService = new GradeService();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("================================================================");
        System.out.println("       STUDENT GRADE MANAGEMENT SYSTEM");
        System.out.println("  CSP3341 - Programming Languages & Paradigms - Part B");
        System.out.println("================================================================\n");
        
        studentService.loadFromFile();
        gradeService.loadFromFile();
        
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice (1-8): ");
            System.out.println();
            
            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewAllStudents(); break;
                case 3: addCourse(); break;
                case 4: recordGrade(); break;
                case 5: viewStudentReport(); break;
                case 6: calculateGPA(); break;
                case 7: viewAcademicStanding(); break;
                case 8: saveAndExit(); running = false; break;
                default: System.out.println("Invalid choice!\n");
            }
        }
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\n================ MAIN MENU ================");
        System.out.println("  1. Add New Student");
        System.out.println("  2. View All Students");
        System.out.println("  3. Add New Course");
        System.out.println("  4. Record Grade");
        System.out.println("  5. View Student Report");
        System.out.println("  6. Calculate Student GPA");
        System.out.println("  7. View Academic Standing");
        System.out.println("  8. Save and Exit");
        System.out.println("===========================================");
    }
    
    private static void addStudent() {
        System.out.println("--- Add New Student ---");
        String id = getStringInput("Enter Student ID: ");
        String name = getStringInput("Enter Student Name: ");
        String email = getStringInput("Enter Student Email: ");
        
        Student student = new Student(id, name, email);
        if (studentService.addStudent(student)) {
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Error: Student ID already exists!");
        }
    }
    
    private static void viewAllStudents() {
        System.out.println("--- All Students ---");
        if (studentService.getAllStudents().isEmpty()) {
            System.out.println("No students registered yet.");
            return;
        }
        for (Student student : studentService.getAllStudents()) {
            System.out.println(student);
        }
    }
    
    private static void addCourse() {
        System.out.println("--- Add New Course ---");
        String code = getStringInput("Enter Course Code: ");
        String name = getStringInput("Enter Course Name: ");
        int credits = getIntInput("Enter Credit Hours: ");
        
        Course course = new Course(code, name, credits);
        gradeService.addCourse(course);
        System.out.println("Course added successfully!");
    }
    
    private static void recordGrade() {
        System.out.println("--- Record Grade ---");
        String studentId = getStringInput("Enter Student ID: ");
        Student student = studentService.getStudent(studentId);
        
        if (student == null) {
            System.out.println("Error: Student not found!");
            return;
        }
        
        String courseCode = getStringInput("Enter Course Code: ");
        double score = getDoubleInput("Enter Score (0-100): ");
        
        if (score < 0 || score > 100) {
            System.out.println("Error: Invalid score!");
            return;
        }
        
        Grade grade = new Grade(studentId, courseCode, score);
        gradeService.recordGrade(grade);
        System.out.println("Grade recorded successfully!");
    }
    
    private static void viewStudentReport() {
        System.out.println("--- Student Report ---");
        String studentId = getStringInput("Enter Student ID: ");
        Student student = studentService.getStudent(studentId);
        
        if (student == null) {
            System.out.println("Error: Student not found!");
            return;
        }
        
        System.out.println(student);
        var grades = gradeService.getStudentGrades(studentId);
        if (grades.isEmpty()) {
            System.out.println("No grades recorded yet.");
            return;
        }
        
        System.out.println("\nGrades:");
        for (Grade grade : grades) {
            System.out.println(grade);
        }
    }
    
    private static void calculateGPA() {
        System.out.println("--- Calculate GPA ---");
        String studentId = getStringInput("Enter Student ID: ");
        Student student = studentService.getStudent(studentId);
        
        if (student == null) {
            System.out.println("Error: Student not found!");
            return;
        }
        
        double gpa = gradeService.calculateGPA(studentId);
        System.out.println("GPA: " + String.format("%.2f", gpa));
    }
    
    private static void viewAcademicStanding() {
        System.out.println("--- Academic Standing ---");
        for (Student student : studentService.getAllStudents()) {
            double gpa = gradeService.calculateGPA(student.getId());
            String standing = determineAcademicStanding(gpa);
            System.out.println(student.getName() + ": " + gpa + " - " + standing);
        }
    }
    
    private static String determineAcademicStanding(double gpa) {
        if (gpa >= 3.5) return "Dean's List";
        if (gpa >= 3.0) return "Good Standing";
        if (gpa >= 2.0) return "Satisfactory";
        return "Probation";
    }
    
    private static void saveAndExit() {
        System.out.println("\nSaving data...");
        studentService.saveToFile();
        gradeService.saveToFile();
        System.out.println("Data saved successfully!");
        System.out.println("Goodbye!\n");
    }
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }
    }
}
