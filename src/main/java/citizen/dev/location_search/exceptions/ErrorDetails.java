package citizen.dev.location_search.exceptions;

import lombok.Getter;

@Getter
public class ErrorDetails {
    private final int statusCode;
    private final String message;
    private final String details;

    public ErrorDetails(int statusCode, String message, String details) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }
}
