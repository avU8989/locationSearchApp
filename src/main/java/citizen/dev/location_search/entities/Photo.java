package citizen.dev.location_search.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "photos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String url;
    private String description;
    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "poi_id")
    private PointOfInterest pointOfInterest;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
}
