package citizen.dev.location_search.services;

import citizen.dev.location_search.entities.Account;
import citizen.dev.location_search.entities.CustomUserDetails;
import citizen.dev.location_search.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            // Fetch authorities/roles if needed
            // Collection<? extends GrantedAuthority> authorities = /* fetch authorities */;

            // Create and return CustomerUserDetails (custom UserDetails implementation)
            return new CustomUserDetails(account, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User with email does not exist: " + email);
        }
    }
}
