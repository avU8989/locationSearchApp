package citizen.dev.location_search.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "nearby_services")
@Getter
@Setter
public class NearbyService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String address;
    private String contactInfo;
    private String openingHours;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; // Link to Category

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "poi_id")
    private PointOfInterest pointOfInterest;
}
