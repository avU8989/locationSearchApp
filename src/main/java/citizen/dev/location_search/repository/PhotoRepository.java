package citizen.dev.location_search.repository;

import citizen.dev.location_search.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
