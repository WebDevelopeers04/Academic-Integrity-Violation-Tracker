
import java.time.LocalDate;

public class CodePlagiarismViolation extends Violation {
    private static final long serialVersionUID = 1L;
    
    private String sourceDetected;
    private double similarityPercentage;
    private String programmingLanguage;
    private String detectionTool;

    // Updated constructor to use Student object
    public CodePlagiarismViolation(Student student, String misconductType, 
                                  LocalDate incidentDate, String reportingFaculty, int gravityLevel, 
                                  String currentStatus, String appliedSanction, String incidentDescription, 
                                  int recordID, String supportingEvidence, String sourceDetected, 
                                  double similarityPercentage, String programmingLanguage, String detectionTool) {
        super(student, misconductType, incidentDate, reportingFaculty, 
              gravityLevel, currentStatus, appliedSanction, incidentDescription, recordID, supportingEvidence);
        this.sourceDetected = sourceDetected;
        this.similarityPercentage = similarityPercentage;
        this.programmingLanguage = programmingLanguage;
        this.detectionTool = detectionTool;
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
    
    public String getProgrammingLanguage() { 
        return programmingLanguage; 
    }
    
    public void setProgrammingLanguage(String programmingLanguage) { 
        this.programmingLanguage = programmingLanguage; 
    }
    
    public String getDetectionTool() { 
        return detectionTool; 
    }
    
    public void setDetectionTool(String detectionTool) { 
        this.detectionTool = detectionTool; 
    }

    @Override
    public String generateReport() {
        return String.format("""
            ==================================================
                      CODE PLAGIARISM CASE REPORT
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
            Code Plagiarism Details:
            Source Detected: %s
            Similarity Percentage: %.1f%%
            Programming Language: %s
            Detection Tool: %s
            ==================================================
            """, 
            recordID, getFullName(), getEnrollmentNumber(), 
            getEmail(), getDepartment(), incidentDate, reportingFaculty,
            gravityLevel, currentStatus, appliedSanction, incidentDescription,
            supportingEvidence, sourceDetected, similarityPercentage, programmingLanguage, detectionTool);
    }
}