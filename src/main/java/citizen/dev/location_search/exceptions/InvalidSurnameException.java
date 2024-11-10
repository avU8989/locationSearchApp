package citizen.dev.location_search.exceptions;

public class InvalidSurnameException extends UserAccountException {
    public InvalidSurnameException(String message) {
        super("Invalid Surname: " + message);
    }

    @Override
    public String toString() {
        return "InvalidSurnameException: " + getMessage();
    }
}
