import java.io.*;
import java.time.LocalDateTime;

public class DataPersistenceManager {
    private static final String DATA_FILE = "aivt_data.ser";

    /**
     * Save CaseManager object to file
     * @param caseManager The CaseManager instance to save
     * @return true if save was successful, false otherwise
     */
    public static boolean saveData(CaseManager caseManager) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(caseManager);
            System.out.println("Data saved successfully to " + DATA_FILE);

            // --- NEW: Create readable text version if manual save ---
            if (isManualSaveTriggered()) {
                generateReadableTextFile(caseManager);
            }

            return true;
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
            return false;
        }
    }

    /**
     * Load CaseManager object from file
     * @return CaseManager instance, or null if no data exists or error occurs
     */
    public static CaseManager loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("No existing data file found. Starting with new CaseManager.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            CaseManager caseManager = (CaseManager) ois.readObject();
            System.out.println("Data loaded successfully from " + DATA_FILE);
            System.out.println("Loaded " + caseManager.getTotalCases() + " cases.");
            return caseManager;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            System.out.println("Starting with new CaseManager.");
            return null;
        }
    }

    /**
     * Generate a human-readable text file with all cases (only during manual save)
     */
    private static void generateReadableTextFile(CaseManager caseManager) {
        String txtFilename = DATA_FILE.replace(".ser", ".txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(txtFilename))) {
            writer.println("==================================================");
            writer.println("  Academic Integrity Violation Tracker (AIVT) v2.0");
            writer.println("==================================================");
            writer.println("Generated on: " + LocalDateTime.now());
            writer.println("Total Cases: " + caseManager.getTotalCases());
            writer.println("==================================================\n");

            for (Violation v : caseManager.getAllCases()) {
                // Each Violation has a detailed report already
                writer.println(v.generateReport());
                writer.println("==================================================\n");
            }

            System.out.println("Readable data saved to " + txtFilename);
        } catch (IOException e) {
            System.out.println("Error generating readable text file: " + e.getMessage());
        }
    }

    /**
     * Detect if the save was triggered manually from Option 11
     */
    private static boolean isManualSaveTriggered() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().contains("AIVTApp")) {
                // Option 11 is handled in AIVTApp — if present, assume manual save
                return true;
            }
        }
        return false;
    }

    public static boolean dataFileExists() {
        return new File(DATA_FILE).exists();
    }

    public static String getDataFileInfo() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            return "Data file: " + DATA_FILE + " (" + String.format("%.2f", file.length() / 1024.0) + " KB)";
        }
        return "No data file exists yet.";
    }

    public static boolean createBackup() {
        File dataFile = new File(DATA_FILE);
        if (!dataFile.exists()) {
            System.out.println("No data file found to backup.");
            return false;
        }

        String backupFile = DATA_FILE.replace(".ser", "_backup.ser");
        try (FileInputStream fis = new FileInputStream(dataFile);
             FileOutputStream fos = new FileOutputStream(backupFile)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            System.out.println("Backup created: " + backupFile);
            return true;
        } catch (IOException e) {
            System.out.println("Error creating backup: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteDataFile() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Data file deleted: " + DATA_FILE);
                return true;
            } else {
                System.out.println("Failed to delete data file: " + DATA_FILE);
                return false;
            }
        }
        System.out.println("Data file does not exist: " + DATA_FILE);
        return false;
    }

    public static String getDataFileStats() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            return "Data File Statistics:\n" +
                    "  File: " + DATA_FILE + "\n" +
                    "  Size: " + String.format("%.2f", file.length() / 1024.0) + " KB\n" +
                    "  Last Modified: " + new java.util.Date(file.lastModified());
        }
        return "Data file does not exist.";
    }
}
