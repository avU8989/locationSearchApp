package citizen.dev.location_search.exceptions;

public class UserAccountException extends ValidationException {
    public UserAccountException(String message) {
        super(message);
    }


    @Override
    public String toString() {
        return "UserAccountException: " + getMessage();
    }
}
