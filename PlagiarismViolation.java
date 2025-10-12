
import java.time.LocalDate;

public class PlagiarismViolation extends Violation {
    private static final long serialVersionUID = 1L;
    
    private String sourceDetected;
    private double similarityPercentage;

    // Updated constructor to use Student object
    public PlagiarismViolation(Student student, String misconductType, 
                              LocalDate incidentDate, String reportingFaculty, int gravityLevel, 
                              String currentStatus, String appliedSanction, String incidentDescription, 
                              int recordID, String supportingEvidence, String sourceDetected, 
                              double similarityPercentage) {
        super(student, misconductType, incidentDate, reportingFaculty, 
              gravityLevel, currentStatus, appliedSanction, incidentDescription, recordID, supportingEvidence);
        this.sourceDetected = sourceDetected;
        this.similarityPercentage = similarityPercentage;
    }

    public String getSourceDetected() { 
        return sourceDetected; 
    }
    
    public void setSourceDetected(String sourceDetected) { 
        this.sourceDetected = sourceDetected; 
    }
    
    public double getSimilarityPercentage() { 
        return similarityPercentage; 
    }
    
    public void setSimilarityPercentage(double similarityPercentage) { 
        this.similarityPercentage = similarityPercentage; 
    }

    @Override
    public String generateReport() {
        return String.format("""
            ==================================================
                         PLAGIARISM CASE REPORT
            ==================================================
            Case ID: %d
            Student: %s (%s)
            Email: %s
            Department: %s
            Incident Date: %s
            Reporting Faculty: %s
            --------------------------------------------------
            Gravity Level: %d/5
            Current Status: %s
            Applied Sanction: %s
            --------------------------------------------------
            Incident Description:
            %s
            --------------------------------------------------
            Supporting Evidence:
            %s
            --------------------------------------------------
            Plagiarism Details:
            Source Detected: %s
            Similarity Percentage: %.1f%%
            ==================================================
            """, 
            recordID, student.getStudentName(), student.getStudentID(), 
            student.getEmail(), student.getDepartment(), incidentDate, reportingFaculty,
            gravityLevel, currentStatus, appliedSanction, incidentDescription,
            supportingEvidence, sourceDetected, similarityPercentage);
    }
}