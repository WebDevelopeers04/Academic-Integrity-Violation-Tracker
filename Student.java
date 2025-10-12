import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String studentID;
    private String studentName;
    private String email;
    private String department;

    public Student(String studentID, String studentName, String email, String department) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.email = email;
        this.department = department;
    }
    
    public String getStudentID() {
        return studentID;
    }
    
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    @Override
    public String toString() {
        return String.format("Student[ID: %s, Name: %s, Email: %s, Department: %s]", 
                           studentID, studentName, email, department);
    }
    
    // Additional helper method for display
    public String getDisplayInfo() {
        return String.format("%s (%s) - %s", studentName, studentID, department);
    }
}