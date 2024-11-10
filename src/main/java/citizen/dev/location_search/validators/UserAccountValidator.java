package citizen.dev.location_search.validators;

import citizen.dev.location_search.entities.Account;
import citizen.dev.location_search.exceptions.InvalidBirthdayException;
import citizen.dev.location_search.exceptions.InvalidEmailException;
import citizen.dev.location_search.exceptions.InvalidPasswordException;
import citizen.dev.location_search.exceptions.InvalidSurnameException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserAccountValidator implements EntityValidator<Account> {

    @Override
    public void validate(Account userAccount) {
        if (userAccount.getEmail() == null || !userAccount.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidEmailException(userAccount.getEmail() + " !");
        }

        if (userAccount.getSurname() == null || userAccount.getSurname().isBlank()) {
            throw new InvalidSurnameException("User surname is missing or invalid!");
        }

        if (!userAccount.getSurname().matches("^[A-Za-z\\s'-]+$")) {
            throw new InvalidSurnameException("Surname can only contain alphabetic characters, spaces, apostrophes, and hyphens!");
        }

        if (userAccount.getPassword() == null || userAccount.getPassword().length() < 8) {
            throw new InvalidPasswordException("Password should be at least 8 characters long!");
        }

        if (userAccount.getBirthday() != null) {
            if (userAccount.getBirthday().isAfter(LocalDate.now())) {
                throw new InvalidBirthdayException("Birthday cannot be in the future!");
            }
        }
    }
}
