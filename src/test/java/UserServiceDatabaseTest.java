import citizen.dev.location_search.LocationSearchApplication;
import citizen.dev.location_search.entities.Account;
import citizen.dev.location_search.repository.AccountRepository;
import citizen.dev.location_search.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = LocationSearchApplication.class)  // Replace with your main application class
public class UserServiceDatabaseTest {
    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void saveAccount_shouldSaveToDatabase() {
        // Arrange
        Account account = new Account();
        account.setEmail("test@example.com");
        account.setPassword("password123");
        account.setSurname("Doe");
        account.setFirstName("John");
        account.setBirthday(LocalDate.of(1990, 1, 1));

        // Act
        userService.saveAccount(account);

        // Assert
        Optional<Account> savedAccount = accountRepository.findByEmail("test@example.com");
        assertTrue(savedAccount.isPresent(), "Account should be present in the database");

    }
}
