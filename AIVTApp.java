import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AIVTApp {
    private CaseManager caseManager;
    private Scanner scanner;
    private InputValidator validator;

    public AIVTApp() {
        this.scanner = new Scanner(System.in);
        this.validator = new InputValidator(scanner);
        this.caseManager = new CaseManager();
        
        // Only initialize test data if no previous data was loaded AND no data file exists
        if (caseManager.getTotalCases() == 0 && !DataPersistenceManager.dataFileExists()) {
            System.out.println("No existing data found. Initializing with sample data...");
            initializeTestData();
        } else if (caseManager.getTotalCases() > 0) {
            System.out.println("Loaded " + caseManager.getTotalCases() + " existing cases from storage.");
        }
        
        System.out.println(DataPersistenceManager.getDataFileInfo());
    }

    // Initializes sample test data for demonstration purposes
    private void initializeTestData() {
        try {
            // Sample plagiarism case
            Student student1 = new Student("20230001", "John Smith", "john.smith@university.edu", "Computer Science");
            PlagiarismViolation plagCase = new PlagiarismViolation(
                student1, "Plagiarism", LocalDate.of(2024, 3, 15),
                "Dr. Johnson", 3, "Under Investigation", "Grade Reduction",
                "Student submitted essay with 75% similarity to online source",
                0, "Turnitin report, original source documentation",
                "Wikipedia and academic papers", 75.3
            );
            caseManager.addCase(plagCase);

            // Sample cheating case
            Student student2 = new Student("20230002", "Mary Davis", "mary.davis@university.edu", "Mathematics");
            CheatingViolation cheatCase = new CheatingViolation(
                student2, "Cheating", LocalDate.of(2024, 3, 20),
                "Prof. Wilson", 4, "Resolved", "Suspension",
                "Student used unauthorized notes during exam",
                0, "Security camera footage, confiscated notes",
                "Hidden notes under desk", "Cheat sheets, smartphone"
            );
            caseManager.addCase(cheatCase);

            // Sample collusion case
            Student student3 = new Student("20230003", "Alex Chen", "alex.chen@university.edu", "Engineering");
            CollusionViolation collusionCase = new CollusionViolation(
                student3, "Collusion", LocalDate.of(2024, 3, 25),
                "Dr. Brown", 2, "Pending", "Warning",
                "Multiple students submitted identical lab reports",
                0, "Identical code submissions, similar formatting",
                "Alex Chen, Sarah Kim, Mike Thompson",
                "Shared lab report with identical results and formatting"
            );
            caseManager.addCase(collusionCase);

            System.out.println("Sample data initialized successfully.");
        } catch (InvalidViolationException e) {
            System.out.println("Error initializing test data: " + e.getMessage());
        }
    }

    // Displays the main menu with all available options
    public void displayMainMenu() {
        System.out.println("\n=========================================================");
        System.out.println("    Academic Integrity Violation Tracker (AIVT) v2.0   ");
        System.out.println("=========================================================");
        System.out.println("1.  Add New Violation Case");
        System.out.println("    - Register a new academic misconduct case");
        System.out.println();
        System.out.println("2.  Search Case by ID");
        System.out.println("    - Find and view details of a specific case");
        System.out.println();
        System.out.println("3.  Search Cases by Student");
        System.out.println("    - View all cases associated with a student");
        System.out.println();
        System.out.println("4.  List All Cases");
        System.out.println("    - Display summary of all recorded cases");
        System.out.println();
        System.out.println("5.  Generate Case Report");
        System.out.println("    - Create detailed report for a specific case");
        System.out.println();
        System.out.println("6.  Update Case Status");
        System.out.println("    - Change the status of an existing case");
        System.out.println();
        System.out.println("7.  Apply Penalty to Case");
        System.out.println("    - Assign sanctions/penalties to a case");
        System.out.println();
        System.out.println("8.  Close Case");
        System.out.println("    - Mark a case as closed and finalized");
        System.out.println();
        System.out.println("9.  Generate Summary Report");
        System.out.println("    - View statistics and overview of all cases");
        System.out.println();
        System.out.println("10. Remove Case");
        System.out.println("    - Permanently delete a case from the system");
        System.out.println();
        System.out.println("11. Save Data Manually");
        System.out.println("    - Force save all data to disk");
        System.out.println();
        System.out.println("12. View System Statistics");
        System.out.println("    - Display detailed system overview and analytics");
        System.out.println();
        System.out.println("0.  Exit");
        System.out.println("    - Save and exit the application");
        System.out.println("=========================================================");
        System.out.printf("Total Cases: %d | Current Date: %s\n", 
                         caseManager.getTotalCases(), LocalDate.now());
        System.out.print("\nSelect an option (0-12): ");
    }

    // Adds a new violation case to the system
    private void addNewCase() {
        try {
            System.out.println("\n==================================================");
            System.out.println("           ADD NEW VIOLATION CASE               ");
            System.out.println("==================================================");
            System.out.println("Please provide the following information:\n");
            
            String enrollmentNumber = validator.getValidEnrollmentNumber("Student Enrollment Number (8 digits): ");
            String fullName = validator.getValidFullName("Student Full Name (min. 6 characters): ");
            String email = validator.getValidEmail("Student Email: ");
            String department = validator.getValidString("Student Department: ", false);

            // Create Student object
            Student student = new Student(enrollmentNumber, fullName, email, department);
            String reportingFaculty = validator.getValidFacultyName("Reporting Faculty Name: ");
            LocalDate incidentDate = validator.getValidDate("Incident Date (YYYY-MM-DD): ");
            
            System.out.println("\nGravity Level Scale:");
            System.out.println("   1 = Very Minor    2 = Minor    3 = Moderate");
            System.out.println("   4 = Serious       5 = Very Serious");
            int gravityLevel = validator.getValidInteger("Gravity Level (1-5): ", 1, 5);
            
            String incidentDescription = validator.getValidDescription("Incident Description (min. 10 characters): ");
            String supportingEvidence = validator.getValidDescription("Supporting Evidence (min. 10 characters): ");

            System.out.println("\n==================================================");
            System.out.println("         SELECT VIOLATION TYPE                  ");
            System.out.println("==================================================");
            System.out.println("1. Plagiarism - Copying content without attribution");
            System.out.println("2. Cheating - Using unauthorized help during exam");
            System.out.println("3. Collusion - Unauthorized collaboration");
            System.out.println("4. Code Plagiarism - Copying source code");
            System.out.println("==================================================");
            
            int choice = validator.getValidInteger("Enter choice (1-4): ", 1, 4);
            Violation violation = null;

            switch (choice) {
                case 1:
                    violation = createPlagiarismCase(student, incidentDate, reportingFaculty, 
                                                    gravityLevel, incidentDescription, supportingEvidence);
                    break;
                case 2:
                    violation = createCheatingCase(student, incidentDate, reportingFaculty, 
                                                  gravityLevel, incidentDescription, supportingEvidence);
                    break;
                case 3:
                    violation = createCollusionCase(student, incidentDate, reportingFaculty, 
                                                   gravityLevel, incidentDescription, supportingEvidence);
                    break;
                case 4:
                    violation = createCodePlagiarismCase(student, incidentDate, reportingFaculty, 
                                                        gravityLevel, incidentDescription, supportingEvidence);
                    break;
            }

            if (violation != null) {
                caseManager.addCase(violation);
                System.out.println("\nCase added successfully!");
                System.out.println("   Case ID: " + violation.getRecordID());
                System.out.println("   Student: " + fullName);
                System.out.println("   Type: " + violation.getMisconductType());
            }

        } catch (InvalidViolationException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
        }
    }

    // Creates a plagiarism violation case with specific details
    private PlagiarismViolation createPlagiarismCase(Student student, LocalDate incidentDate, 
                                                    String reportingFaculty, int gravityLevel, 
                                                    String incidentDescription, String supportingEvidence) {
        System.out.println("\n--- Plagiarism Case Specific Details ---");
        String sourceDetected = validator.getValidDescription("Source Detected (min. 10 characters): ");
        
        System.out.println("\nNote: Similarity percentage indicates how much content matches the source");
        double similarityPercentage = validator.getValidDouble("Similarity Percentage (0.0-100.0): ", 0.0, 100.0);

        return new PlagiarismViolation(student, "Plagiarism", incidentDate, reportingFaculty, 
                                      gravityLevel, "Pending", "None", incidentDescription, 
                                      0, supportingEvidence, sourceDetected, similarityPercentage);
    }

    // Creates a cheating violation case with specific details
    private CheatingViolation createCheatingCase(Student student, LocalDate incidentDate, 
                                                String reportingFaculty, int gravityLevel, 
                                                String incidentDescription, String supportingEvidence) {
        System.out.println("\n--- Cheating Case Specific Details ---");
        String cheatingMethod = validator.getValidDescription("Cheating Method (min. 10 characters): ");
        String unauthorizedMaterials = validator.getValidDescription("Unauthorized Materials Used (min. 10 characters): ");

        return new CheatingViolation(student, "Cheating", incidentDate, reportingFaculty, 
                                    gravityLevel, "Pending", "None", incidentDescription, 
                                    0, supportingEvidence, cheatingMethod, unauthorizedMaterials);
    }

    // Creates a collusion violation case with specific details
    private CollusionViolation createCollusionCase(Student student, LocalDate incidentDate, 
                                                  String reportingFaculty, int gravityLevel, 
                                                  String incidentDescription, String supportingEvidence) {
        System.out.println("\n--- Collusion Case Specific Details ---");
        String involvedParties = validator.getValidDescription("Involved Parties (min. 10 characters, comma-separated): ");
        String collaborationDetails = validator.getValidDescription("Collaboration Details (min. 10 characters): ");

        return new CollusionViolation(student, "Collusion", incidentDate, reportingFaculty, 
                                     gravityLevel, "Pending", "None", incidentDescription, 
                                     0, supportingEvidence, involvedParties, collaborationDetails);
    }

    // Creates a code plagiarism violation case with specific details
    private CodePlagiarismViolation createCodePlagiarismCase(Student student, LocalDate incidentDate, 
                                                            String reportingFaculty, int gravityLevel, 
                                                            String incidentDescription, String supportingEvidence) {
        System.out.println("\n--- Code Plagiarism Case Specific Details ---");
        String sourceDetected = validator.getValidDescription("Source Detected (min. 10 characters): ");
        
        System.out.println("\nNote: Similarity percentage indicates code match level");
        double similarityPercentage = validator.getValidDouble("Similarity Percentage (0.0-100.0): ", 0.0, 100.0);
        
        String programmingLanguage = validator.getValidString("Programming Language: ", false);
        String detectionTool = validator.getValidString("Detection Tool Used: ", false);

        return new CodePlagiarismViolation(student, "Code Plagiarism", incidentDate, reportingFaculty, 
                                          gravityLevel, "Pending", "None", incidentDescription, 
                                          0, supportingEvidence, sourceDetected, similarityPercentage, 
                                          programmingLanguage, detectionTool);
    }

    // Searches for a specific case by its unique ID
    private void searchCaseById() {
        System.out.println("\n==================================================");
        System.out.println("            SEARCH CASE BY ID                   ");
        System.out.println("==================================================");
        System.out.println("Search for detailed information about a specific case");
        System.out.println();
        
        displayDetailedCaseList();
        
        if (caseManager.getTotalCases() == 0) {
            System.out.println("\nNo cases available to search.");
            return;
        }
        
        int recordID = validator.getValidInteger("\nEnter Case ID to search: ", 1000, Integer.MAX_VALUE);
        
        Violation violation = caseManager.searchCase(recordID);
        if (violation != null) {
            System.out.println("\nCase Found!");
            System.out.println("==================================================");
            System.out.println("               CASE DETAILS                     ");
            System.out.println("==================================================");
            System.out.println(violation.generateReport());
        } else {
            System.out.println("\nCase ID " + recordID + " not found.");
            System.out.println("Please check the Case ID from the list above and try again.");
        }
    }

    // Searches for all cases associated with a specific student
    private void searchCasesByStudent() {
        System.out.println("\n==================================================");
        System.out.println("         SEARCH CASES BY STUDENT               ");
        System.out.println("==================================================");
        System.out.println("Find all violation cases associated with a specific student");
        System.out.println();
        
        // Show existing students for reference
        displayExistingStudents();
        
        String enrollmentNumber = validator.getValidEnrollmentNumber("Enter Student Enrollment Number (8 digits): ");

        List<Violation> cases = caseManager.searchByStudent(enrollmentNumber);
        if (cases.isEmpty()) {
            System.out.println("\nNo cases found for student enrollment number: " + enrollmentNumber);
            System.out.println("Please verify the enrollment number from the list above.");
        } else {
            System.out.println("\nFound " + cases.size() + " case(s) for student: " + enrollmentNumber);
            System.out.println("Student Name: " + (cases.get(0).getFullName()));
            System.out.println("==================================================================================");
            for (Violation violation : cases) {
                System.out.printf("Case ID: %-6d | Type: %-20s | Date: %-10s\n", 
                    violation.getRecordID(), violation.getMisconductType(), 
                    violation.getIncidentDate());
                System.out.printf("Status: %-15s | Gravity: %d/5 | Sanction: %-15s\n",
                    violation.getCurrentStatus(), violation.getGravityLevel(),
                    truncateString(violation.getAppliedSanction(), 15));
                System.out.println("----------------------------------------------------------------------------------");
            }
            System.out.println("==================================================================================");
            
            // Option to view detailed report
            if (cases.size() == 1) {
                boolean viewDetails = validator.getYesNoConfirmation("\n📄 Would you like to view the detailed report for this case?");
                if (viewDetails) {
                    System.out.println("\n" + cases.get(0).generateReport());
                }
            } else if (cases.size() > 1) {
                boolean viewDetails = validator.getYesNoConfirmation("\n📄 Would you like to view detailed report for a specific case?");
                if (viewDetails) {
                    displayDetailedCaseList(); // Show available IDs again
                    int specificID = validator.getValidInteger("Enter Case ID for detailed view: ", 1000, Integer.MAX_VALUE);
                    Violation specificCase = caseManager.searchCase(specificID);
                    if (specificCase != null && specificCase.getEnrollmentNumber().equals(enrollmentNumber)) {
                        System.out.println("\n" + specificCase.generateReport());
                    } else {
                        System.out.println("❌ Case ID not found or doesn't belong to this student.");
                    }
                }
            }
        }
    }

    // Generates a detailed report for a specific case
    private void generateCaseReport() {
        System.out.println("\n==================================================");
        System.out.println("         GENERATE DETAILED CASE REPORT          ");
        System.out.println("==================================================");
        System.out.println("Create a comprehensive report for a specific case");
        System.out.println();
        
        displayDetailedCaseList();
        
        if (caseManager.getTotalCases() == 0) {
            System.out.println("\nNo cases available to generate reports.");
            return;
        }
        
        int recordID = validator.getValidInteger("\nEnter Case ID for report: ", 1000, Integer.MAX_VALUE);

        Violation violation = caseManager.searchCase(recordID);
        if (violation != null) {
            System.out.println("\nGenerating Report for Case ID: " + recordID);
            System.out.println("==================================================");
            System.out.println(violation.generateReport());
            
            // Option to save report to file
            boolean saveToFile = validator.getYesNoConfirmation("\n💾 Would you like to save this report to a file?");
            if (saveToFile) {
                saveReportToFile(violation);
            }
        } else {
            System.out.println("\nCase ID " + recordID + " not found.");
            System.out.println("Please check the Case ID from the list above.");
        }
    }

    // Updates the status of an existing case
    private void updateCaseStatus() {
        System.out.println("\n==================================================");
        System.out.println("            UPDATE CASE STATUS                  ");
        System.out.println("==================================================");
        System.out.println("Change the status of an existing case");
        System.out.println();
        
        displayDetailedCaseList();
        
        if (caseManager.getTotalCases() == 0) {
            System.out.println("\nNo cases available to update.");
            return;
        }
        
        int recordID = validator.getValidInteger("\nEnter Case ID to update: ", 1000, Integer.MAX_VALUE);

        Violation violation = caseManager.searchCase(recordID);
        if (violation != null) {
            System.out.println("\nCurrent Status: " + violation.getCurrentStatus());
            System.out.println("Student: " + violation.getFullName());
            System.out.println("Case Type: " + violation.getMisconductType());
            System.out.println("\nAvailable Status Options:");
            System.out.println("  - Pending - Case has been filed, awaiting review");
            System.out.println("  - Under Investigation - Case is being actively investigated");
            System.out.println("  - Resolved - Case has been concluded with decision");
            System.out.println("  - Closed - Case is finalized and archived");
            
            String[] validStatuses = {"Pending", "Under Investigation", "Resolved", "Closed"};
            String newStatus = validator.getValidChoice("\nEnter new status: ", validStatuses);

            violation.updateStatus(newStatus);
            caseManager.saveData();
            System.out.println("\nStatus updated successfully!");
            System.out.println("   Case ID: " + recordID);
            System.out.println("   Student: " + violation.getFullName());
            System.out.println("   New Status: " + newStatus);
        } else {
            System.out.println("\nCase ID " + recordID + " not found.");
            System.out.println("Please check the Case ID from the list above.");
        }
    }

    // Applies a penalty to an existing case
    private void applyPenalty() {
        System.out.println("\n==================================================");
        System.out.println("            APPLY PENALTY TO CASE               ");
        System.out.println("==================================================");
        System.out.println("Assign sanctions/penalties to a case");
        System.out.println();
        
        displayDetailedCaseList();
        
        if (caseManager.getTotalCases() == 0) {
            System.out.println("\nNo cases available to apply penalties.");
            return;
        }
        
        int recordID = validator.getValidInteger("\nEnter Case ID: ", 1000, Integer.MAX_VALUE);

        Violation violation = caseManager.searchCase(recordID);
        if (violation != null) {
            System.out.println("\nCurrent Penalty: " + violation.getAppliedSanction());
            System.out.println("Student: " + violation.getFullName());
            System.out.println("Case Type: " + violation.getMisconductType());
            System.out.println("Gravity Level: " + violation.getGravityLevel() + "/5");
            System.out.println("\nAvailable Penalty Options:");
            System.out.println("  - Warning - Formal written warning");
            System.out.println("  - Grade Reduction - Reduce grade for assignment/course");
            System.out.println("  - Retake Assignment - Must redo the assignment");
            System.out.println("  - Suspension - Temporary suspension from institution");
            System.out.println("  - Expulsion - Permanent removal from institution");
            
            String[] validPenalties = {"Warning", "Grade Reduction", "Retake Assignment", "Suspension", "Expulsion"};
            String penalty = validator.getValidChoice("\nSelect penalty: ", validPenalties);

            CaseResolution resolution = new CaseResolution(violation);
            resolution.applyPenalty(penalty);
            caseManager.saveData();
            System.out.println("\nPenalty applied successfully!");
            System.out.println("   Case ID: " + recordID);
            System.out.println("   Student: " + violation.getFullName());
            System.out.println("   Penalty: " + penalty);
        } else {
            System.out.println("\nCase ID " + recordID + " not found.");
            System.out.println("Please check the Case ID from the list above.");
        }
    }

    // Closes an existing case (marks as finalized)
    private void closeCase() {
        System.out.println("\n==================================================");
        System.out.println("              CLOSE CASE                        ");
        System.out.println("==================================================");
        System.out.println("Note: Closing a case marks it as finalized and archived.\n");
        
        displayDetailedCaseList();
        
        if (caseManager.getTotalCases() == 0) {
            System.out.println("\nNo cases available to close.");
            return;
        }
        
        int recordID = validator.getValidInteger("\nEnter Case ID to close: ", 1000, Integer.MAX_VALUE);

        Violation violation = caseManager.searchCase(recordID);
        if (violation != null) {
            if (violation.getCurrentStatus() != null && violation.getCurrentStatus().equalsIgnoreCase("Closed")) {
                System.out.println("\nCase ID " + recordID + " is already closed.");
                System.out.println("   Closure Date: " + violation.getClosureDate());
                return;
            }

            System.out.println("\nase Details:");
            System.out.println("   Student: " + violation.getFullName());
            System.out.println("   Type: " + violation.getMisconductType());
            System.out.println("   Current Status: " + violation.getCurrentStatus());
            System.out.println("   Applied Sanction: " + violation.getAppliedSanction());
            System.out.println("   Gravity Level: " + violation.getGravityLevel() + "/5");
            System.out.println("   Incident Date: " + violation.getIncidentDate());

            boolean confirm = validator.getYesNoConfirmation("\nAre you sure you want to close Case ID " + recordID + "?");
            
            if (confirm) {
                CaseResolution resolution = new CaseResolution(violation);
                resolution.closeCase();
                caseManager.saveData();
                System.out.println("\nCase closed successfully!");
                System.out.println("   Case ID: " + recordID);
                System.out.println("   Student: " + violation.getFullName());
                System.out.println("   Closure Date: " + LocalDate.now());
            } else {
                System.out.println("\nAction cancelled. Case ID " + recordID + " remains open.");
            }
        } else {
            System.out.println("\nCase ID " + recordID + " not found.");
            System.out.println("Please check the Case ID from the list above.");
        }
    }

    // Permanently removes a case from the system
    private void removeCase() {
        System.out.println("\n==================================================");
        System.out.println("              REMOVE CASE                       ");
        System.out.println("==================================================");
        System.out.println("WARNING: This action permanently deletes the case!\n");
        
        displayDetailedCaseList();
        
        if (caseManager.getTotalCases() == 0) {
            System.out.println("\nNo cases available to remove.");
            return;
        }
        
        int recordID = validator.getValidInteger("\nEnter Case ID to remove: ", 1000, Integer.MAX_VALUE);

        Violation violation = caseManager.searchCase(recordID);
        if (violation == null) {
            System.out.println("\nCase ID " + recordID + " not found.");
            System.out.println("Please check the Case ID from the list above.");
            return;
        }

        System.out.println("\n🚨 PERMANENT DELETION WARNING 🚨");
        System.out.println("==================================================");
        System.out.println("You are about to PERMANENTLY DELETE:");
        System.out.println("   Case ID: " + recordID);
        System.out.println("   Student: " + violation.getFullName() + " (" + violation.getEnrollmentNumber() + ")");
        System.out.println("   Type: " + violation.getMisconductType());
        System.out.println("   Status: " + violation.getCurrentStatus());
        System.out.println("   Gravity Level: " + violation.getGravityLevel() + "/5");
        System.out.println("   Incident Date: " + violation.getIncidentDate());
        System.out.println("==================================================");
        
        boolean confirm = validator.getYesNoConfirmation("\n🚨 This action CANNOT be undone! Continue?");

        if (confirm) {
            caseManager.removeCase(recordID);
            System.out.println("\nCase ID " + recordID + " removed successfully.");
        } else {
            System.out.println("\nAction cancelled. Case ID " + recordID + " was not removed.");
        }
    }

    // Manually saves all data to disk
    private void saveDataManually() {
        System.out.println("\n==================================================");
        System.out.println("            MANUAL DATA SAVE                    ");
        System.out.println("==================================================");
        System.out.println("Note: Data is automatically saved after each operation.");
        System.out.println("This option allows you to force save all data.\n");
        System.out.println(DataPersistenceManager.getDataFileInfo());
        
        boolean confirm = validator.getYesNoConfirmation("Force save all data now?");
        
        if (confirm) {
            caseManager.saveData();
            System.out.println("\nData saved successfully!");
            System.out.println("   Total cases saved: " + caseManager.getTotalCases());
            System.out.println("   Timestamp: " + LocalDate.now());
            System.out.println("   " + DataPersistenceManager.getDataFileInfo());
        } else {
            System.out.println("\nManual save cancelled.");
        }
    }

    // Displays detailed system statistics
    private void viewSystemStatistics() {
        System.out.println("\n==================================================");
        System.out.println("           SYSTEM STATISTICS                    ");
        System.out.println("==================================================");
        
        if (caseManager.getTotalCases() == 0) {
            System.out.println("No cases in the system.");
            return;
        }
        
        // Display summary report
        System.out.println(caseManager.generateSummaryReport());
        
        // Display detailed case list
        displayDetailedCaseList();
        
        // Display student statistics
        displayStudentStatistics();
    }

    // Enhanced display methods
    private void displayDetailedCaseList() {
        if (caseManager.getTotalCases() == 0) {
            System.out.println("No cases currently in the system.");
            return;
        }
        
        System.out.println("\nALL CASES IN SYSTEM (" + caseManager.getTotalCases() + " total):");
        System.out.println("==========================================================================================================");
        System.out.println("Case ID  | Student Name          | Enrollment  | Type           | Status         | Gravity | Date       ");
        System.out.println("==========================================================================================================");
        
        for (Violation v : caseManager.getAllCases()) {
            System.out.printf("%-8d | %-21s | %-11s | %-14s | %-14s | %d/5      | %-10s\n",
                v.getRecordID(),
                truncateString(v.getFullName(), 21),
                v.getEnrollmentNumber(),
                truncateString(v.getMisconductType(), 14),
                truncateString(v.getCurrentStatus(), 14),
                v.getGravityLevel(),
                v.getIncidentDate()
            );
        }
        System.out.println("==========================================================================================================");
    }

    // Helper method to display existing students
    private void displayExistingStudents() {
        if (caseManager.getTotalCases() == 0) {
            return;
        }
        
        System.out.println("👥 EXISTING STUDENTS IN SYSTEM:");
        System.out.println("==================================================");
        System.out.println("Enrollment  | Student Name          | Department");
        System.out.println("==================================================");
        
        java.util.Set<String> displayedStudents = new java.util.HashSet<>();
        for (Violation v : caseManager.getAllCases()) {
            String enrollment = v.getEnrollmentNumber();
            if (!displayedStudents.contains(enrollment)) {
                System.out.printf("%-11s | %-21s | %-20s\n",
                    enrollment,
                    truncateString(v.getFullName(), 21),
                    truncateString(v.getDepartment(), 20));
                displayedStudents.add(enrollment);
            }
        }
        System.out.println("==================================================");
        System.out.println("Total unique students: " + displayedStudents.size());
    }

    // Display student statistics
    private void displayStudentStatistics() {
        if (caseManager.getTotalCases() == 0) {
            return;
        }
        
        java.util.Map<String, Integer> studentCaseCounts = new java.util.HashMap<>();
        java.util.Map<String, String> studentNames = new java.util.HashMap<>();
        
        for (Violation v : caseManager.getAllCases()) {
            String enrollment = v.getEnrollmentNumber();
            studentCaseCounts.put(enrollment, studentCaseCounts.getOrDefault(enrollment, 0) + 1);
            studentNames.put(enrollment, v.getFullName());
        }
        
        System.out.println("\nSTUDENT CASE STATISTICS:");
        System.out.println("==================================================");
        System.out.println("Enrollment  | Student Name          | Case Count");
        System.out.println("==================================================");
        
        studentCaseCounts.entrySet().stream()
            .sorted(java.util.Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(entry -> {
                String enrollment = entry.getKey();
                System.out.printf("%-11s | %-21s | %d case(s)\n",
                    enrollment,
                    truncateString(studentNames.get(enrollment), 21),
                    entry.getValue());
            });
        System.out.println("==================================================");
    }

    // Helper method to save report to file
    private void saveReportToFile(Violation violation) {
        try {
            String filename = "Case_Report_" + violation.getRecordID() + "_" + 
                             violation.getFullName().replace(" ", "_") + ".txt";
            java.io.FileWriter writer = new java.io.FileWriter(filename);
            writer.write(violation.generateReport());
            writer.close();
            System.out.println("Report saved to: " + filename);
        } catch (java.io.IOException e) {
            System.out.println("Error saving report to file: " + e.getMessage());
        }
    }

    // Helper method to truncate long strings for display
    private String truncateString(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }

    // Main application loop
    public void run() {
        System.out.println("\n==================================================");
        System.out.println("                                                  ");
        System.out.println("    Welcome to Academic Integrity Violation      ");
        System.out.println("              Tracker (AIVT v2.0)                ");
        System.out.println("                                                  ");
        System.out.println("   - Track and manage academic misconduct cases  ");
        System.out.println("   - Automatic data persistence enabled          ");
        System.out.println("   - Secure case management system               ");
        System.out.println("                                                  ");
        System.out.println("==================================================");

        while (true) {
            try {
                displayMainMenu();
                String choice = scanner.nextLine().trim();

                if (choice.isEmpty()) {
                    System.out.println("\nInput cannot be empty. Please enter a menu option (0-12).");
                    continue;
                }

                if (!choice.matches("\\d+")) {
                    System.out.println("\nInvalid input. Please enter a number between 0 and 12.");
                    continue;
                }

                int option = Integer.parseInt(choice);
                switch (option) {
                    case 1:
                        addNewCase();
                        break;
                    case 2:
                        searchCaseById();
                        break;
                    case 3:
                        searchCasesByStudent();
                        break;
                    case 4:
                        caseManager.listCases();
                        break;
                    case 5:
                        generateCaseReport();
                        break;
                    case 6:
                        updateCaseStatus();
                        break;
                    case 7:
                        applyPenalty();
                        break;
                    case 8:
                        closeCase();
                        break;
                    case 9:
                        System.out.println("\n" + caseManager.generateSummaryReport());
                        break;
                    case 10:
                        removeCase();
                        break;
                    case 11:
                        saveDataManually();
                        break;
                    case 12:
                        viewSystemStatistics();
                        break;
                    case 0:
                        System.out.println("\n==================================================");
                        System.out.println("                    EXITING AIVT                ");
                        System.out.println("==================================================");
                        System.out.println("Saving final data...");
                        System.out.println("All data has been saved successfully!");
                        System.out.println("\nThank you for using AIVT. Goodbye!");
                        System.out.println("==================================================");
                        return;
                    default:
                        System.out.println("\nInvalid option. Please enter a number between 0 and 12.");
                }

                System.out.println("\n" + "=".repeat(50));
                System.out.print("Press Enter to continue...");
                scanner.nextLine();

            } catch (Exception e) {
                System.out.println("\nAn unexpected error occurred: " + e.getMessage());
                System.out.println("Please try again or contact support if the problem persists.");
            }
        }
    }

    public static void main(String[] args) {
        AIVTApp app = new AIVTApp();
        app.run();
    }
}