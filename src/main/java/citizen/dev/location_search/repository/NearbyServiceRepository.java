package citizen.dev.location_search.repository;

import citizen.dev.location_search.entities.NearbyService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NearbyServiceRepository extends JpaRepository<NearbyService, Long> {
}
