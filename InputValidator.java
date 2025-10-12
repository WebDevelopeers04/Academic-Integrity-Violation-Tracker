import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputValidator {
    private Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    // Enhanced enrollment number validation - exactly 8 digits
    public String getValidEnrollmentNumber(String prompt) {
        String enrollmentNumber;
        while (true) {
            System.out.print(prompt);
            enrollmentNumber = scanner.nextLine().trim();
            
            if (enrollmentNumber.isEmpty()) {
                System.out.println("Error: Enrollment number cannot be empty.");
                continue;
            }
            
            // Must be exactly 8 digits
            if (!enrollmentNumber.matches("\\d{8}")) {
                System.out.println("Error: Enrollment number must be exactly 8 digits (e.g., 20230001).");
                continue;
            }
            
            break;
        }
        return enrollmentNumber;
    }

    // Enhanced full name validation - minimum 6 characters, allows letters, spaces, and hyphens
    public String getValidFullName(String prompt) {
        String fullName;
        while (true) {
            System.out.print(prompt);
            fullName = scanner.nextLine().trim();
            
            if (fullName.isEmpty()) {
                System.out.println("Error: Full name cannot be empty.");
                continue;
            }
            
            if (fullName.length() < 6) {
                System.out.println("Error: Full name must be at least 6 characters long.");
                continue;
            }
            
            if (!fullName.matches("^[a-zA-Z\\s\\-.'']+$")) {
                System.out.println("Error: Full name can only contain letters, spaces, hyphens, and apostrophes.");
                continue;
            }
            
            if (fullName.length() > 100) {
                System.out.println("Error: Full name too long (maximum 100 characters).");
                continue;
            }
            
            break;
        }
        return fullName;
    }

    // Email validation with proper format checking
    public String getValidEmail(String prompt) {
        String email;
        while (true) {
            System.out.print(prompt);
            email = scanner.nextLine().trim();
            
            if (email.isEmpty()) {
                System.out.println("Error: Email address cannot be empty.");
                continue;
            }
            
            // Basic email format validation using regex
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);
            
            if (!pattern.matcher(email).matches()) {
                System.out.println("Error: Invalid email format. Please enter a valid email address (e.g., user@example.com).");
                continue;
            }
            
            if (email.length() > 254) {
                System.out.println("Error: Email address too long (maximum 254 characters).");
                continue;
            }
            
            break;
        }
        return email;
    }

    // Enhanced faculty name validation
    public String getValidFacultyName(String prompt) {
        String facultyName;
        while (true) {
            System.out.print(prompt);
            facultyName = scanner.nextLine().trim();
            
            if (facultyName.isEmpty()) {
                System.out.println("Error: Faculty name cannot be empty.");
                continue;
            }
            
            if (facultyName.length() < 3) {
                System.out.println("Error: Faculty name must be at least 3 characters long.");
                continue;
            }
            
            if (!facultyName.matches("^[a-zA-Z\\s\\-.'',&()]+$")) {
                System.out.println("Error: Faculty name contains invalid characters.");
                continue;
            }
            
            break;
        }
        return facultyName;
    }

    // Enhanced description validation - minimum 10 characters
    public String getValidDescription(String prompt) {
        String description;
        while (true) {
            System.out.print(prompt);
            description = scanner.nextLine().trim();
            
            if (description.isEmpty()) {
                System.out.println("Error: Description cannot be empty.");
                continue;
            }
            
            if (description.length() < 10) {
                System.out.println("Error: Description must be at least 10 characters long.");
                continue;
            }
            
            if (description.length() > 1000) {
                System.out.println("Error: Description too long (maximum 1000 characters).");
                continue;
            }
            
            break;
        }
        return description;
    }

    // General string validation
    public String getValidString(String prompt, boolean allowEmpty) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            
            if (input.isEmpty() && !allowEmpty) {
                System.out.println("Error: Input cannot be empty.");
                continue;
            }
            
            if (input.length() > 500) {
                System.out.println("Error: Input too long (max 500 characters).");
                continue;
            }
            
            break;
        }
        return input;
    }

    public int getValidInteger(String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println("Error: Input cannot be empty.");
                continue;
            }
            
            try {
                value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.printf("Error: Number must be between %d and %d.\n", min, max);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format. Please enter a valid integer.");
            }
        }
        return value;
    }

    public double getValidDouble(String prompt, double min, double max) {
        double value;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println("Error: Input cannot be empty.");
                continue;
            }
            
            try {
                value = Double.parseDouble(input);
                if (value < min || value > max) {
                    System.out.printf("Error: Number must be between %.2f and %.2f.\n", min, max);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format. Please enter a valid decimal number.");
            }
        }
        return value;
    }

    public LocalDate getValidDate(String prompt) {
        LocalDate date;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println("Error: Date cannot be empty.");
                continue;
            }
            
            try {
                date = LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);
                if (date.isAfter(LocalDate.now())) {
                    System.out.println("Error: Date cannot be in the future.");
                    continue;
                }
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format. Please use YYYY-MM-DD format (e.g., 2024-03-15).");
            }
        }
        return date;
    }

    public String getValidChoice(String prompt, String[] validOptions) {
        String choice;
        while (true) {
            System.out.print(prompt);
            choice = scanner.nextLine().trim();
            
            if (choice.isEmpty()) {
                System.out.println("Error: Choice cannot be empty.");
                System.out.println("Valid options: " + String.join(", ", validOptions));
                continue;
            }
            
            // Check if choice matches any valid option (case-insensitive)
            boolean isValid = false;
            for (String option : validOptions) {
                if (choice.equalsIgnoreCase(option)) {
                    choice = option; // Return the properly formatted option
                    isValid = true;
                    break;
                }
            }
            
            if (!isValid) {
                System.out.println("Error: Invalid choice.");
                System.out.println("Valid options: " + String.join(", ", validOptions));
                continue;
            }
            
            break;
        }
        return choice;
    }

    public boolean getYesNoConfirmation(String prompt) {
        String response;
        while (true) {
            System.out.print(prompt + " (yes/no): ");
            response = scanner.nextLine().trim().toLowerCase();
            
            if (response.isEmpty()) {
                System.out.println("Error: Please enter 'yes' or 'no'.");
                continue;
            }
            
            if (response.equals("yes") || response.equals("y")) {
                return true;
            } else if (response.equals("no") || response.equals("n")) {
                return false;
            } else {
                System.out.println("Error: Please enter 'yes' or 'no'.");
            }
        }
    }
}