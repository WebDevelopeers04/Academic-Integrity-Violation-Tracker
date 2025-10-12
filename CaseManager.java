import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CaseManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Violation> cases;
    private AtomicInteger nextCaseId;

    public CaseManager() {
        // Try to load existing data first
        CaseManager loadedManager = DataPersistenceManager.loadData();

        if (loadedManager != null) {
            // Deep copy data
            this.cases = new ArrayList<>(loadedManager.cases);
            this.nextCaseId = new AtomicInteger(loadedManager.nextCaseId.get());
        } else {
            // Initialize with defaults
            this.cases = new ArrayList<>();
            this.nextCaseId = new AtomicInteger(1000);
            initializeSampleData();
        }
    }

    private void initializeSampleData() {
        try {
            Student student1 = new Student("20230001", "John Smith", "john.smith@university.edu", "Computer Science");
            Student student2 = new Student("20230002", "Mary Davis", "mary.davis@university.edu", "Mathematics");
            Student student3 = new Student("20230003", "Alex Chen", "alex.chen@university.edu", "Engineering");

            PlagiarismViolation plagCase = new PlagiarismViolation(
                    student1, "Plagiarism",
                    java.time.LocalDate.of(2024, 3, 15), "Dr. Johnson", 3,
                    "Under Investigation", "Grade Reduction",
                    "Student submitted essay with 75% similarity to online source",
                    0, "Turnitin report, original source documentation",
                    "Wikipedia and academic papers", 75.3
            );

            CheatingViolation cheatCase = new CheatingViolation(
                    student2, "Cheating",
                    java.time.LocalDate.of(2024, 3, 20), "Prof. Wilson", 4,
                    "Resolved", "Suspension",
                    "Student used unauthorized notes during exam",
                    0, "Security camera footage, confiscated notes",
                    "Hidden notes under desk", "Cheat sheets, smartphone"
            );

            CollusionViolation collusionCase = new CollusionViolation(
                    student3, "Collusion",
                    java.time.LocalDate.of(2024, 3, 25), "Dr. Brown", 2,
                    "Pending", "Warning",
                    "Multiple students submitted identical lab reports",
                    0, "Identical code submissions, similar formatting",
                    "Alex Chen, Sarah Kim, Mike Thompson",
                    "Shared lab report with identical results and formatting"
            );

            addCase(plagCase);
            addCase(cheatCase);
            addCase(collusionCase);

            System.out.println("✅ Sample data initialized successfully.");
        } catch (InvalidViolationException e) {
            System.out.println("❌ Error initializing sample data: " + e.getMessage());
        }
    }

    public void addCase(Violation violation) throws InvalidViolationException {
        if (violation == null) throw new InvalidViolationException("Violation cannot be null");
        violation.setRecordID(nextCaseId.getAndIncrement());
        cases.add(violation);
        saveData();
    }

    public Violation searchCase(int recordID) {
        return cases.stream()
                .filter(v -> v.getRecordID() == recordID)
                .findFirst()
                .orElse(null);
    }

    public List<Violation> searchByStudent(String enrollmentNumber) {
        List<Violation> result = new ArrayList<>();
        for (Violation v : cases) {
            if (v.getStudent().getStudentID().equals(enrollmentNumber)) {
                result.add(v);
            }
        }
        return result;
    }

    public void removeCase(int recordID) {
        cases.removeIf(v -> v.getRecordID() == recordID);
        saveData();
    }

    public List<Violation> getAllCases() {
        return new ArrayList<>(cases);
    }

    public int getTotalCases() {
        return cases.size();
    }

    public void listCases() {
        if (cases.isEmpty()) {
            System.out.println("No cases in the system.");
            return;
        }

        System.out.println("\n==================================================");
        System.out.println("               ALL CASES SUMMARY                  ");
        System.out.println("==================================================");
        System.out.printf("%-8s | %-20s | %-15s | %-12s | %s\n",
                "Case ID", "Student", "Type", "Status", "Gravity");
        System.out.println("--------------------------------------------------");

        for (Violation v : cases) {
            System.out.printf("%-8d | %-20s | %-15s | %-12s | %d/5\n",
                    v.getRecordID(),
                    truncateString(v.getFullName(), 20),
                    truncateString(v.getMisconductType(), 15),
                    truncateString(v.getCurrentStatus(), 12),
                    v.getGravityLevel());
        }

        System.out.println("==================================================");
    }

    public String generateSummaryReport() {
        if (cases.isEmpty()) {
            return "No cases available for summary report.";
        }

        StringBuilder report = new StringBuilder();
        report.append("\n==================================================\n");
        report.append("              SUMMARY REPORT                     \n");
        report.append("==================================================\n");
        report.append("Total Cases: ").append(cases.size()).append("\n");

        // Cases by type
        report.append("\n--- Cases by Type ---\n");
        cases.stream()
                .collect(java.util.stream.Collectors.groupingBy(Violation::getMisconductType,
                        java.util.stream.Collectors.counting()))
                .forEach((type, count) -> report.append(String.format("%-20s: %d\n", type, count)));

        // Cases by status
        report.append("\n--- Cases by Status ---\n");
        cases.stream()
                .collect(java.util.stream.Collectors.groupingBy(Violation::getCurrentStatus,
                        java.util.stream.Collectors.counting()))
                .forEach((status, count) -> report.append(String.format("%-20s: %d\n", status, count)));

        // Cases by gravity
        report.append("\n--- Cases by Gravity Level ---\n");
        cases.stream()
                .collect(java.util.stream.Collectors.groupingBy(Violation::getGravityLevel,
                        java.util.stream.Collectors.counting()))
                .forEach((gravity, count) -> report.append(String.format("Level %-15d: %d\n", gravity, count)));

        report.append("==================================================\n");
        return report.toString();
    }

    public void saveData() {
        DataPersistenceManager.saveData(this);
    }

    private String truncateString(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
}
