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

@SpringBootTest(classes = LocationSearchApplication.class)
public class UserServiceDatabaseTest {
    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void saveAccount_shouldSaveToDatabase() {
        Account account = new Account();
        account.setEmail("test@example.com");
        account.setPassword("password123");
        account.setSurname("Doe");
        account.setFirstname("John");
        account.setBirthday(LocalDate.of(1990, 1, 1));

        userService.saveAccount(account);

        Optional<Account> savedAccount = accountRepository.findByEmail("test@example.com");
        assertTrue(savedAccount.isPresent(), "Account should be present in the database");

    }
}
