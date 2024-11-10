package citizen.dev.location_search.exceptions;

public class UserAccountAlreadyExistsException extends RuntimeException {
    public UserAccountAlreadyExistsException(String message) {
        super(message);
    }
}
