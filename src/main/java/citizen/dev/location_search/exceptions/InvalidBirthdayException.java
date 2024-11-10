package citizen.dev.location_search.exceptions;

public class InvalidBirthdayException extends UserAccountException {

    public InvalidBirthdayException(String message) {
        super("Invalid birthday: " + message);
    }
}
