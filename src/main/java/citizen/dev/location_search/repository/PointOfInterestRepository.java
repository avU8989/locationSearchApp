package citizen.dev.location_search.repository;

import citizen.dev.location_search.entities.PointOfInterest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointOfInterestRepository extends JpaRepository<PointOfInterest, Long> {
}
