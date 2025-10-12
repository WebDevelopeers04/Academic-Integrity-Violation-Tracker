public class InvalidViolationException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidViolationException(String message) {
        super(message);
    }

    public InvalidViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}