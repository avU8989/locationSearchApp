package citizen.dev.location_search.exceptions;

public class InvalidEmailException extends UserAccountException {
    public InvalidEmailException(String message) {
        super("Invalid Email: " + message);

    }

    @Override
    public String toString() {
        return "InvalidEmailException: " + getMessage();
    }
}
