package citizen.dev.location_search.services;

import citizen.dev.location_search.entities.Account;
import citizen.dev.location_search.exceptions.UserAccountAlreadyExistsException;
import citizen.dev.location_search.repository.AccountRepository;
import citizen.dev.location_search.validators.UserAccountValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountValidator userAccountValidator;

    @Autowired
    public UserService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, UserAccountValidator userAccountValidator) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.userAccountValidator = userAccountValidator;
    }

    @Transactional
    public void saveAccount(Account account) {
        if (accountRepository.findByEmail(account.getEmail()).isPresent()) {
            throw new UserAccountAlreadyExistsException("Email is already used");
        } else {
            logger.info("Saving account");
            userAccountValidator.validate(account);
            String encodedPassword = passwordEncoder.encode(account.getPassword());
            account.setPassword(encodedPassword);
            accountRepository.save(account);
        }
    }

}
