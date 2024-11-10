package citizen.dev.location_search.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


//represents palces of interest at a location, such as historical landmarks, museums, or parks
@Entity
@Table(name = "point_of_interests")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PointOfInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String description;
    private String latitude;
    private String longitude;
    private String address;
    private String openingHours;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "pointOfInterest")
    private List<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; // Link to Category

    @OneToMany(mappedBy = "pointOfInterest")
    private List<NearbyService> nearbyServices; // Services around the POI

    @OneToMany(mappedBy = "pointOfInterest")
    private List<Photo> photos; // Photos associated with this POI
}
