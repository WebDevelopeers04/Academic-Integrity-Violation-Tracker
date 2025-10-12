import java.io.Serializable;
import java.time.LocalDate;

public abstract class Violation implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected int recordID;
    protected Student student; // Changed from separate fields to Student object
    protected String misconductType;
    protected LocalDate incidentDate;
    protected String reportingFaculty;
    protected int gravityLevel;
    protected String currentStatus;
    protected String appliedSanction;
    protected String incidentDescription;
    protected LocalDate closureDate;
    protected String supportingEvidence;

    // Updated constructor to use Student object
    public Violation(Student student, String misconductType, 
                    LocalDate incidentDate, String reportingFaculty, int gravityLevel, 
                    String currentStatus, String appliedSanction, String incidentDescription, 
                    int recordID, String supportingEvidence) {
        this.student = student;
        this.misconductType = misconductType;
        this.incidentDate = incidentDate;
        this.reportingFaculty = reportingFaculty;
        this.gravityLevel = gravityLevel;
        this.currentStatus = currentStatus;
        this.appliedSanction = appliedSanction;
        this.incidentDescription = incidentDescription;
        this.recordID = recordID;
        this.supportingEvidence = supportingEvidence;
        this.closureDate = null;
    }

    // Getters and setters
    public int getRecordID() { 
        return recordID; 
    }
    
    public void setRecordID(int recordID) { 
        this.recordID = recordID; 
    }
    
    public Student getStudent() { 
        return student; 
    }
    
    public void setStudent(Student student) { 
        this.student = student; 
    }
    
    // Convenience methods to access student properties
    public String getEnrollmentNumber() { 
        return student.getStudentID(); 
    }
    
    public String getFullName() { 
        return student.getStudentName(); 
    }
    
    public String getEmail() { 
        return student.getEmail(); 
    }
    
    public String getDepartment() { 
        return student.getDepartment(); 
    }
    
    public String getMisconductType() { 
        return misconductType; 
    }
    
    public void setMisconductType(String misconductType) { 
        this.misconductType = misconductType; 
    }
    
    public LocalDate getIncidentDate() { 
        return incidentDate; 
    }
    
    public void setIncidentDate(LocalDate incidentDate) { 
        this.incidentDate = incidentDate; 
    }
    
    public String getReportingFaculty() { 
        return reportingFaculty; 
    }
    
    public void setReportingFaculty(String reportingFaculty) { 
        this.reportingFaculty = reportingFaculty; 
    }
    
    public int getGravityLevel() { 
        return gravityLevel; 
    }
    
    public void setGravityLevel(int gravityLevel) { 
        this.gravityLevel = gravityLevel; 
    }
    
    public String getCurrentStatus() { 
        return currentStatus; 
    }
    
    public void setCurrentStatus(String currentStatus) { 
        this.currentStatus = currentStatus; 
    }
    
    public String getAppliedSanction() { 
        return appliedSanction; 
    }
    
    public void setAppliedSanction(String appliedSanction) { 
        this.appliedSanction = appliedSanction; 
    }
    
    public String getIncidentDescription() { 
        return incidentDescription; 
    }
    
    public void setIncidentDescription(String incidentDescription) { 
        this.incidentDescription = incidentDescription; 
    }
    
    public LocalDate getClosureDate() { 
        return closureDate; 
    }
    
    public void setClosureDate(LocalDate closureDate) { 
        this.closureDate = closureDate; 
    }
    
    public String getSupportingEvidence() { 
        return supportingEvidence; 
    }
    
    public void setSupportingEvidence(String supportingEvidence) { 
        this.supportingEvidence = supportingEvidence; 
    }

    public void updateStatus(String newStatus) {
        this.currentStatus = newStatus;
        if ("Closed".equalsIgnoreCase(newStatus)) {
            this.closureDate = LocalDate.now();
        }
    }

    public abstract String generateReport();
    
    @Override
    public String toString() {
        return String.format("Case ID: %d | Student: %s | Type: %s | Status: %s | Gravity: %d/5",
                recordID, student.getStudentName(), misconductType, currentStatus, gravityLevel);
    }
}