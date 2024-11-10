import citizen.dev.location_search.entities.Account;
import citizen.dev.location_search.repository.AccountRepository;
import citizen.dev.location_search.services.UserService;
import citizen.dev.location_search.validators.UserAccountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserAccountValidator userAccountValidator;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveAccount_shouldCreateAccountSuccessfully() {
        // Arrange
        Account account = new Account();
        account.setEmail("test@example.com");
        account.setPassword("password123");
        account.setSurname("Doe");
        account.setBirthday(LocalDate.of(1990, 1, 1));

        when(accountRepository.findByEmail(account.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(account.getPassword())).thenReturn("encodedPassword");

        // Act
        userService.saveAccount(account);

        // Assert
        verify(userAccountValidator).validate(account);  // Validate account was checked
        verify(passwordEncoder).encode("password123");    // Password was encoded
        verify(accountRepository).save(account);          // Account was saved
    }
}
