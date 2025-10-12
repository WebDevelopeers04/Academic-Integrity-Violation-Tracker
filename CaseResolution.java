import java.time.LocalDate;

public class CaseResolution {
    private Violation violation;

    public CaseResolution(Violation violation) {
        this.violation = violation;
    }

    public void applyPenalty(String penalty) {
        violation.setAppliedSanction(penalty);
        if ("Resolved".equalsIgnoreCase(violation.getCurrentStatus()) || 
            "Closed".equalsIgnoreCase(violation.getCurrentStatus())) {
            violation.updateStatus("Resolved");
        }
    }

    public void closeCase() {
        violation.updateStatus("Closed");
        violation.setClosureDate(LocalDate.now());
    }

    public void reopenCase() {
        violation.updateStatus("Under Investigation");
        violation.setClosureDate(null);
    }

    public String getResolutionSummary() {
        return String.format("Case ID: %d | Student: %s | Status: %s | Sanction: %s | Closure Date: %s",
                violation.getRecordID(), violation.getFullName(), violation.getCurrentStatus(),
                violation.getAppliedSanction(), violation.getClosureDate());
    }
}