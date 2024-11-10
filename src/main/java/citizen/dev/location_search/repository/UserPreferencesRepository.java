package citizen.dev.location_search.repository;

import citizen.dev.location_search.entities.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long> {
}
