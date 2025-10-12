
import java.time.LocalDate;

public class CheatingViolation extends Violation {
    private static final long serialVersionUID = 1L;
    
    private String cheatingMethod;
    private String unauthorizedMaterials;

    // Updated constructor to use Student object
    public CheatingViolation(Student student, String misconductType, 
                           LocalDate incidentDate, String reportingFaculty, int gravityLevel, 
                           String currentStatus, String appliedSanction, String incidentDescription, 
                           int recordID, String supportingEvidence, String cheatingMethod, 
                           String unauthorizedMaterials) {
        super(student, misconductType, incidentDate, reportingFaculty, 
              gravityLevel, currentStatus, appliedSanction, incidentDescription, recordID, supportingEvidence);
        this.cheatingMethod = cheatingMethod;
        this.unauthorizedMaterials = unauthorizedMaterials;
    }

    public String getCheatingMethod() { 
        return cheatingMethod; 
    }
    
    public void setCheatingMethod(String cheatingMethod) { 
        this.cheatingMethod = cheatingMethod; 
    }
    
    public String getUnauthorizedMaterials() { 
        return unauthorizedMaterials; 
    }
    
    public void setUnauthorizedMaterials(String unauthorizedMaterials) { 
        this.unauthorizedMaterials = unauthorizedMaterials; 
    }

    @Override
    public String generateReport() {
        return String.format("""
            ==================================================
                          CHEATING CASE REPORT
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
            Cheating Details:
            Cheating Method: %s
            Unauthorized Materials: %s
            ==================================================
            """, 
            recordID, getFullName(), getEnrollmentNumber(), 
            getEmail(), getDepartment(), incidentDate, reportingFaculty,
            gravityLevel, currentStatus, appliedSanction, incidentDescription,
            supportingEvidence, cheatingMethod, unauthorizedMaterials);
    }
}