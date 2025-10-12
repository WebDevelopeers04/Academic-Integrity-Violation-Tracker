
import java.time.LocalDate;

public class CollusionViolation extends Violation {
    private static final long serialVersionUID = 1L;
    
    private String involvedParties;
    private String collaborationDetails;

    // Updated constructor to use Student object
    public CollusionViolation(Student student, String misconductType, 
                            LocalDate incidentDate, String reportingFaculty, int gravityLevel, 
                            String currentStatus, String appliedSanction, String incidentDescription, 
                            int recordID, String supportingEvidence, String involvedParties, 
                            String collaborationDetails) {
        super(student, misconductType, incidentDate, reportingFaculty, 
              gravityLevel, currentStatus, appliedSanction, incidentDescription, recordID, supportingEvidence);
        this.involvedParties = involvedParties;
        this.collaborationDetails = collaborationDetails;
    }

    public String getInvolvedParties() { 
        return involvedParties; 
    }
    
    public void setInvolvedParties(String involvedParties) { 
        this.involvedParties = involvedParties; 
    }
    
    public String getCollaborationDetails() { 
        return collaborationDetails; 
    }
    
    public void setCollaborationDetails(String collaborationDetails) { 
        this.collaborationDetails = collaborationDetails; 
    }

    @Override
    public String generateReport() {
        return String.format("""
            ==================================================
                         COLLUSION CASE REPORT
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
            Collusion Details:
            Involved Parties: %s
            Collaboration Details: %s
            ==================================================
            """, 
            recordID, getFullName(), getEnrollmentNumber(), 
            getEmail(), getDepartment(), incidentDate, reportingFaculty,
            gravityLevel, currentStatus, appliedSanction, incidentDescription,
            supportingEvidence, involvedParties, collaborationDetails);
    }
}