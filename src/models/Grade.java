package models;

public class Grade {
    private String studentId;
    private String courseCode;
    private double score;
    private String letterGrade;
    private double gradePoints;
    
    public Grade(String studentId, String courseCode, double score) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.score = score;
        this.letterGrade = calculateLetterGrade(score);
        this.gradePoints = calculateGradePoints(score);
    }
    
    private String calculateLetterGrade(double score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }
    
    private double calculateGradePoints(double score) {
        if (score >= 90) return 4.0;
        if (score >= 80) return 3.0;
        if (score >= 70) return 2.0;
        if (score >= 60) return 1.0;
        return 0.0;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public double getScore() {
        return score;
    }
    
    public String getLetterGrade() {
        return letterGrade;
    }
    
    public double getGradePoints() {
        return gradePoints;
    }
    
    @Override
    public String toString() {
        return "Grade[Student=" + studentId + ", Course=" + courseCode + 
               ", Score=" + score + ", Letter=" + letterGrade + ", Points=" + gradePoints + "]";
    }
}
