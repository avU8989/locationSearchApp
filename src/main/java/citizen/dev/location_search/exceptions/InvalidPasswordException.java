package citizen.dev.location_search.exceptions;

public class InvalidPasswordException extends UserAccountException {
    public InvalidPasswordException(String message) {
        super("Invalid password: " + message);
    }
}
