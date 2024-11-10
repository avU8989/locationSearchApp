package citizen.dev.location_search.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "ValidationException: " + getMessage();
    }
}
