package citizen.dev.location_search.repository;

import citizen.dev.location_search.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
